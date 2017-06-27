package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.MemberProductCommentDTO;
import com.lawu.eshop.member.api.MemberApiConfig;
import com.lawu.eshop.member.api.service.*;
import com.lawu.eshop.product.dto.MemberProductCommentInfoDTO;
import com.lawu.eshop.product.dto.MemberProductModelDTO;
import com.lawu.eshop.product.dto.MemberProductStoreDTO;
import com.lawu.eshop.product.dto.ProductInfoDTO;
import com.lawu.eshop.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description:商品
 * </p>
 *
 * @author Yangqh
 * @date 2017年3月27日 下午2:40:23
 */
@Api(tags = "product")
@RestController
@RequestMapping(value = "product/")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	@Autowired
	private MerchantStoreService merchantStoreService;
	@Autowired
	private FansMerchantService fansMerchantService;
	@Autowired
	private CommentMerchantService commentMerchantService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private FavoriteProductService favoriteProductService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private MemberApiConfig memberApiConfig;

	@Audit(date = "2017-04-01", reviewer = "孙林青")
	@ApiOperation(value = "查询商品详情", notes = "根据商品ID查询商品详情信息，[]，（杨清华）", httpMethod = "GET")
	@RequestMapping(value = "{productId}", method = RequestMethod.GET)
	public Result<ProductInfoDTO> selectProductById(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@PathVariable @ApiParam(name = "productId", required = true, value = "商品ID") Long productId) {

		Result<ProductInfoDTO> result = productService.selectProductById(productId);
		if (result.getRet() != ResultCode.SUCCESS) {
			return result;
		}
		
//		Result<MerchantBaseInfoDTO> merchantResult = merchantService.getMerchantById(result.getModel().getMerchantId());
//		result.getModel().setMerchantUserNum(merchantResult.getModel().getUserNum());

		// 查询门店信息
		Result<MemberProductStoreDTO> storeDTOResult = merchantStoreService.getMemberProductDetailStore(result.getModel().getMerchantId());
		if (result.getRet() != ResultCode.SUCCESS) {
			return result;
		}
		if(storeDTOResult.getModel() != null){
			Result<Integer> productNumResult = productService.selectProductCount(result.getModel().getMerchantId());
			storeDTOResult.getModel().setUpProductNum(productNumResult.getModel()== null ? "0" : productNumResult.getModel().toString());//上架商品数量
			Result<Integer> fansNumResult = fansMerchantService.countFans(result.getModel().getMerchantId());
			storeDTOResult.getModel().setFansNum(fansNumResult.getModel() == null ? "0" : fansNumResult.getModel().toString());//商家粉丝数量
		}
		result.getModel().setStore(storeDTOResult.getModel());		

		List<MemberProductModelDTO> spec = result.getModel().getSpec();//用于下面查询评价时回显商品型号名称
		
		// 查询评价信息
		Result<List<MemberProductCommentDTO>> commentsResult = commentMerchantService.geNewlyProductComment(productId);
		List<MemberProductCommentInfoDTO> commentList = new ArrayList<MemberProductCommentInfoDTO>();
		if (commentsResult.getRet() == ResultCode.SUCCESS) {
			for (MemberProductCommentDTO comment : commentsResult.getModel()) {
				MemberProductCommentInfoDTO cidto = new MemberProductCommentInfoDTO();
				cidto.setContent(comment.getContent());
				cidto.setGmtCreate(comment.getGmtCreate());
				cidto.setGrade(comment.getGrade());
				cidto.setImageUrl(comment.getImgUrls());
				cidto.setIsAnonymous(comment.getIsAnonymous());
				cidto.setReplyContent(comment.getReplyContent());

				//Result<com.lawu.eshop.product.dto.CommentProductInfoDTO> product = productService.selectCommentProductInfo(comment.getProductModelId());
				boolean flag = true;
				for(MemberProductModelDTO model : spec){
					if(comment.getProductModelId().longValue() == model.getId().longValue()){
						cidto.setProductModel(model.getName());
						flag = false;
						break;
					}
				}
				if(flag){
					cidto.setProductModel("");
				}

				Result<UserDTO> user = memberService.findMemberInfo(comment.getMemberId());
				String nickName = user.getModel().getNickname();
				if(cidto.getIsAnonymous()){
					cidto.setHeadUrl(memberApiConfig.getDefaultHeadimg());
					cidto.setName(nickName.substring(0,1)+"***"+nickName.substring(nickName.length()-1,nickName.length()));
				}else{
					cidto.setHeadUrl(user.getModel().getHeadimg());
					cidto.setName(nickName);
				}
				cidto.setLevel(String.valueOf(user.getModel().getLevel()));
				commentList.add(cidto);
			}
		}
		result.getModel().setComments(commentList);

		Result<Integer> count = commentMerchantService.getProductCommentCount(productId);
		result.getModel().setCommentCount(count.getModel());

		Long userId = UserUtil.getCurrentUserId(getRequest());
		if (userId == 0L) {
			result.getModel().setFavoriteFlag(false);
			result.getModel().setShoppingCartNum("0");
		} else {
			boolean isFavorite = favoriteProductService.getUserFavorite(productId, userId);
			result.getModel().setFavoriteFlag(isFavorite);
			Result<Long> shoppingCart = shoppingCartService.findListByIds(userId);
			if(shoppingCart.getModel() == null){
				result.getModel().setShoppingCartNum("0");
			}else{
				result.getModel().setShoppingCartNum(shoppingCart.getModel().toString());
			}
		}
		return result;
	}

}
