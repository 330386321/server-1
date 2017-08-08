package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.FileDirConstant;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.CommentDTO;
import com.lawu.eshop.mall.dto.CommentGradeDTO;
import com.lawu.eshop.mall.dto.CommentProductDTO;
import com.lawu.eshop.mall.param.CommentProductListParam;
import com.lawu.eshop.mall.param.CommentProductParam;
import com.lawu.eshop.member.api.MemberApiConfig;
import com.lawu.eshop.member.api.service.CommentProductService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.ProductService;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.order.dto.CommentOrderDTO;
import com.lawu.eshop.product.dto.CommentProductInfoDTO;
import com.lawu.eshop.user.constants.UploadFileTypeConstant;
import com.lawu.eshop.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.UploadFileUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyong
 * @date 2017/4/5.
 */
@Api(tags = "commentProduct")
@RestController
@RequestMapping(value = "commentProduct/")
public class CommentProductController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CommentProductController.class);
    @Autowired
    private CommentProductService commentProductService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingOrderService shoppingOrderService;

    @Autowired
    private MemberApiConfig memberApiConfig;

    @Audit(date = "2017-04-15", reviewer = "孙林青")
    @Deprecated
    @ApiOperation(value = "用户评价商品", notes = "用户评价商品 [1005，1000,4100]（章勇）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "saveCommentProductInfo", method = RequestMethod.POST)
    public Result saveCommentProductInfo(@ModelAttribute @ApiParam CommentProductParam param,@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        HttpServletRequest request = getRequest();
        Long memberId = UserUtil.getCurrentUserId(request);
        //查询订单项是否已经评价
        Result<CommentOrderDTO> commentOrderDTO = shoppingOrderService.getOrderCommentStatus(param.getShoppingOrderItemId());
        if(commentOrderDTO.getModel()==null){
            return successCreated(ResultCode.RESOURCE_NOT_FOUND);
        }
        if(commentOrderDTO.getModel().getEvaluation()){
            return successCreated(ResultCode.PRODUCT_EVALUATE_TRUE);
        }
        StringBuilder headImg = new StringBuilder();
        Collection<Part> parts = null;
        try {
                parts = request.getParts();

        } catch (IOException e) {
            logger.info("IO异常：{}",e);
            return successCreated(e.getMessage());
        }
        catch (ServletException ex){
            logger.info("Servlet异常：{}",ex);
        }
        if(parts != null && StringUtils.isNotEmpty(parts.toString())) {
            for (Part part : parts) {
                Map<String, String> map = UploadFileUtil.uploadImages(request, FileDirConstant.DIR_STORE, part, memberApiConfig.getImageUploadUrl());
                String flag = map.get("resultFlag");
                if (UploadFileTypeConstant.UPLOAD_RETURN_TYPE.equals(flag)) {
                    //有图片上传成功返回,拼接图片url
                    String imgUrl = map.get("imgUrl");
                    headImg.append(imgUrl + ",");
                } else {
                    return successCreated(Integer.valueOf(flag));
                }
            }
        }
        return commentProductService.saveCommentProductInfo(memberId, param, headImg.toString());
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "评价商品列表(全部)", notes = "评价商品列表 [1002，1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getCommentProducts", method = RequestMethod.GET)
    public Result<Page<CommentProductDTO>> getCommentProducts(@ModelAttribute @ApiParam CommentProductListParam listParam){

        List<CommentProductDTO> commentProductDTOS = new ArrayList<>();
        Page<CommentProductDTO> pages = new Page<>();
        //获取评论列表
        Result<Page<CommentDTO>> result = commentProductService.getCommentProducts(listParam);
        if(result.getModel() == null || result.getModel() .getRecords().isEmpty()){
            pages.setCurrentPage(listParam.getCurrentPage());
            pages.setTotalCount(result.getModel().getTotalCount());
            pages.setRecords(new ArrayList<>());
            return successGet(pages);
        }
            for (CommentDTO commentDTO : result.getModel().getRecords()) {
                //设置评论信息
                CommentProductDTO commentProductDTO = new CommentProductDTO();
                commentProductDTO.setContent(commentDTO.getContent());
                commentProductDTO.setReplyContent(commentDTO.getReplyContent());
                commentProductDTO.setAnonymous(commentDTO.getAnonymous());
                commentProductDTO.setGmtCreate(commentDTO.getGmtCreate());
                commentProductDTO.setImgUrls(commentDTO.getImgUrls());
                commentProductDTO.setId(commentDTO.getId());
                commentProductDTO.setGrade(commentDTO.getGrade());
                //查询评论用户信息
                Result<UserDTO> user = memberService.findMemberInfo(commentDTO.getMemberId());

                if(commentDTO.getAnonymous()){
                    commentProductDTO.setHeadImg(memberApiConfig.getDefaultHeadimg());
                    commentProductDTO.setNickName(user.getModel().getNickname().substring(0,1)+"***"+user.getModel().getNickname().substring(user.getModel().getNickname().length()-1,user.getModel().getNickname().length()));
                }else{
                    commentProductDTO.setHeadImg(user.getModel().getHeadimg());
                    commentProductDTO.setNickName(user.getModel().getNickname());
                }
                commentProductDTO.setLevel(user.getModel().getLevel());
                //查询商品信息
                Result<CommentProductInfoDTO>  product = productService.selectCommentProductInfo(commentDTO.getProductModelId());
                commentProductDTO.setName(product.getModel().getName());
                commentProductDTO.setPrice(product.getModel().getPrice());
                commentProductDTO.setSpec(product.getModel().getModelName());
                commentProductDTOS.add(commentProductDTO);
            }
        pages.setCurrentPage(result.getModel().getCurrentPage());
        pages.setTotalCount(result.getModel().getTotalCount());
        pages.setRecords(commentProductDTOS);
        return successGet(pages);
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
    @ApiOperation(value = "评价商品列表（有图）", notes = "评价商品列表（有图） [1002，1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getCommentProductsWithImgs", method = RequestMethod.GET)
    public Result<Page<CommentProductDTO>> getCommentProductsWithImgs(@ModelAttribute @ApiParam CommentProductListParam listParam) {

        List<CommentProductDTO> commentProductDTOS = new ArrayList<>();
        Page<CommentProductDTO> pages = new Page<>();
        //获取评论列表
        Result<Page<CommentDTO>> result = commentProductService.getCommentProductsWithImgs(listParam);
        if(result.getModel() == null || result.getModel() .getRecords().isEmpty()){
            pages.setCurrentPage(listParam.getCurrentPage());
            pages.setTotalCount(result.getModel().getTotalCount());
            pages.setRecords(new ArrayList<>());
            return successGet(pages);
        }
            for (CommentDTO commentDTO : result.getModel().getRecords()) {
                //设置评论信息
                CommentProductDTO commentProductDTO = new CommentProductDTO();
                commentProductDTO.setContent(commentDTO.getContent());
                commentProductDTO.setReplyContent(commentDTO.getReplyContent());
                commentProductDTO.setAnonymous(commentDTO.getAnonymous());
                commentProductDTO.setGmtCreate(commentDTO.getGmtCreate());
                commentProductDTO.setImgUrls(commentDTO.getImgUrls());
                commentProductDTO.setId(commentDTO.getId());
                commentProductDTO.setGrade(commentDTO.getGrade());
                //查询评论用户信息
                Result<UserDTO> user = memberService.findMemberInfo(commentDTO.getMemberId());
                if(commentDTO.getAnonymous()){
                    commentProductDTO.setHeadImg(memberApiConfig.getDefaultHeadimg());
                    commentProductDTO.setNickName(user.getModel().getNickname().substring(0,1)+"***"+user.getModel().getNickname().substring(user.getModel().getNickname().length()-1,user.getModel().getNickname().length()));
                }else{
                    commentProductDTO.setHeadImg(user.getModel().getHeadimg());
                    commentProductDTO.setNickName(user.getModel().getNickname());
                }
                commentProductDTO.setLevel(user.getModel().getLevel());
                //查询商品信息
                //查询商品信息
                Result<CommentProductInfoDTO>  product = productService.selectCommentProductInfo(commentDTO.getProductModelId());
                commentProductDTO.setName(product.getModel().getName());
                commentProductDTO.setPrice(product.getModel().getPrice());
                commentProductDTO.setSpec(product.getModel().getModelName());
                commentProductDTOS.add(commentProductDTO);
            }
        pages.setCurrentPage(result.getModel().getCurrentPage());
        pages.setTotalCount(result.getModel().getTotalCount());
        pages.setRecords(commentProductDTOS);
        return successGet(pages);
    }

    @Audit(date = "2017-04-12", reviewer = "孙林青")
   // @ApiOperation(value = "查询商品评价好评率，综合评分", notes = "查询商品评价好评率，综合评分 [1004，1000]（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getCommentProductAvgGrade/{productId}", method = RequestMethod.GET)
    public Result<CommentGradeDTO> getCommentAvgGrade(@PathVariable("productId") @ApiParam(value = "商品ID",required = true) Long productId){
        if(productId == null){
            return successGet(ResultCode.REQUIRED_PARM_EMPTY);
        }
        return commentProductService.getCommentAvgGrade(productId);
    }

    @Audit(date = "2017-08-08", reviewer = "孙林青")
    @ApiOperation(value = "用户评价商品(new)", notes = "用户评价商品 [1005，1000,4100]（章勇）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "addCommentProductInfo", method = RequestMethod.POST)
    public Result addCommentProductInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                         @ModelAttribute @ApiParam CommentProductParam param,
                                        @RequestParam(required = false) @ApiParam(value = "图片路径") String imageUrls) {
        Long memberId = UserUtil.getCurrentUserId(getRequest());
        if(imageUrls == null){
            imageUrls =",";
        }
        return commentProductService.saveCommentProductInfo(memberId, param, imageUrls);
    }

}
