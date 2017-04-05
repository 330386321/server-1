package com.lawu.eshop.member.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.mall.dto.MemberShoppingCartDTO;
import com.lawu.eshop.mall.dto.ShoppingCartDTO;
import com.lawu.eshop.mall.param.ShoppingCartParam;
import com.lawu.eshop.mall.param.ShoppingCartSaveParam;
import com.lawu.eshop.mall.param.ShoppingCartUpdateParam;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.member.api.service.ProductModelService;
import com.lawu.eshop.member.api.service.ShoppingCartService;
import com.lawu.eshop.product.dto.ShoppingCartProductModelDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny 
 * @date 2017/3/27
 */
@Api(tags = "shoppingCart")
@RestController
@RequestMapping(value = "shoppingCart/")
public class ShoppingCartController extends BaseController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    
    @Autowired
    private ProductModelService productModelService;
    
    @Autowired
    private MerchantStoreService merchantStoreService;
    
    /**
     * 加入购物车。
     * 
     * @param token
     * @param param
     * @return
     */
	@Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "加入购物车", notes = "加入购物车。[1004|1005]（蒋鑫俊）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value = "加入购物车参数") ShoppingCartParam param) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	Result<ShoppingCartProductModelDTO> resultShoppingCartProductModelDTO = productModelService.getShoppingCartProductModel(param.getProductModelId());
    	
    	if (!isSuccess(resultShoppingCartProductModelDTO)) {
    		return successCreated(resultShoppingCartProductModelDTO.getRet());
    	}
    	
    	ShoppingCartProductModelDTO shoppingCartProductModelDTO = resultShoppingCartProductModelDTO.getModel();
    	
    	Result<String> resultMerchantName = merchantStoreService.getNameByMerchantId(shoppingCartProductModelDTO.getMerchantId());
    	
    	if (!isSuccess(resultMerchantName)) {
    		return successCreated(resultMerchantName.getRet());
    	}
    	
    	ShoppingCartSaveParam shoppingCartSaveParam = new ShoppingCartSaveParam();
    	shoppingCartSaveParam.setMerchantName(resultMerchantName.getModel());
    	shoppingCartSaveParam.setMerchantId(shoppingCartProductModelDTO.getMerchantId());
    	shoppingCartSaveParam.setProductId(shoppingCartProductModelDTO.getProductId());
    	shoppingCartSaveParam.setProductModelId(shoppingCartProductModelDTO.getId());
    	shoppingCartSaveParam.setQuantity(param.getQuantity());
    	shoppingCartSaveParam.setSalesPrice(shoppingCartProductModelDTO.getPrice());
    	
    	return successCreated(shoppingCartService.save(memberId, shoppingCartSaveParam));
    }

    /**
     * 根据memberId查询用户的购物车列表。
     * 
     * @param token
     * @return
     */
	@Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "查询用户的购物车列表", notes = "根据memberId查询用户的购物车列表。[1004]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "findListByMemberId", method = RequestMethod.GET)
    Result<List<MemberShoppingCartDTO>> findListByMemberId(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	// 通过memberId查询用户购物车资料
    	Result<List<ShoppingCartDTO>> resultShoppingCartDTOS = shoppingCartService.findListByMemberId(memberId);
    	if (!isSuccess(resultShoppingCartDTOS)) {
    		return successGet(resultShoppingCartDTOS.getRet());
    	}
    	
    	// 把要查询的id放入set,统一一次性查询
    	Set<Long> ids = new HashSet<Long>();
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		ids.add(shoppingCartDTO.getProductModelId());
    	}
    	
    	// 通过商品型号id列表查询商品信息
    	Result<List<ShoppingCartProductModelDTO>> resultShoppingCartProductModelDTOS = productModelService.getShoppingCartProductModel(new ArrayList<Long>(ids));
    	if (!isSuccess(resultShoppingCartProductModelDTOS)) {
    		return successGet(resultShoppingCartProductModelDTOS.getRet());
    	}
    	
    	// 组装数据
    	Map<Long, ShoppingCartProductModelDTO> shoppingCartProductModelDTOMap = new HashMap<Long, ShoppingCartProductModelDTO>();
    	for (ShoppingCartProductModelDTO shoppingCartProductModelDTO : resultShoppingCartProductModelDTOS.getModel()) {
    		if (!shoppingCartProductModelDTOMap.containsKey(shoppingCartProductModelDTO.getId())) {
    			shoppingCartProductModelDTOMap.put(shoppingCartProductModelDTO.getId(), shoppingCartProductModelDTO);
    		}
    	}
    	
    	List<MemberShoppingCartDTO> MemberShoppingCartDTOS = new ArrayList<MemberShoppingCartDTO>();
    	
    	ShoppingCartProductModelDTO shoppingCartProductModelDTO = null;
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		MemberShoppingCartDTO memberShoppingCartDTO = new MemberShoppingCartDTO();
    		memberShoppingCartDTO.setId(shoppingCartDTO.getId());
    		memberShoppingCartDTO.setMerchantName(shoppingCartDTO.getMerchantName());
    		memberShoppingCartDTO.setProductId(shoppingCartDTO.getProductId());
    		memberShoppingCartDTO.setProductModelId(shoppingCartDTO.getProductModelId());
    		memberShoppingCartDTO.setQuantity(shoppingCartDTO.getQuantity());
    		memberShoppingCartDTO.setSalesPrice(shoppingCartDTO.getSalesPrice());
    		
    		shoppingCartProductModelDTO = shoppingCartProductModelDTOMap.get(shoppingCartDTO.getProductModelId());
    		if (shoppingCartProductModelDTO == null) {
    			continue;
    		}
    		memberShoppingCartDTO.setProductModelName(shoppingCartProductModelDTO.getName());
    		memberShoppingCartDTO.setProductName(shoppingCartProductModelDTO.getProductName());
    		memberShoppingCartDTO.setFeatureImage(shoppingCartProductModelDTO.getFeatureImage());
    		memberShoppingCartDTO.setSalesPrice(shoppingCartProductModelDTO.getPrice());
    		// 计算差价(商品表的现价减去购物车表价格,正为涨价,负为降价)
    		memberShoppingCartDTO.setDifference(shoppingCartProductModelDTO.getPrice().subtract(shoppingCartDTO.getSalesPrice()));
    		if (shoppingCartProductModelDTO.getStatus().equals(((byte)0x01)) || shoppingCartProductModelDTO.getStatus().equals(((byte)0x03))) {
    			memberShoppingCartDTO.setIsInvalid(true);
    		} else {
    			memberShoppingCartDTO.setIsInvalid(false);
    		}
    		
    		MemberShoppingCartDTOS.add(memberShoppingCartDTO);
    	}
    	
    	return successGet(MemberShoppingCartDTOS);
    }
    
    /**
     * 根据id更新购物车的商品（使用实时更新不采用批量更新的方式）。
     * 
     * @param id
     * @param token
     * @param parm
     * @return
     */
	@Audit(date = "2017-04-01", reviewer = "孙林青")
    @ApiOperation(value = "更新购物车商品", notes = "根据id更新购物车的商品（使用实时更新不采用批量更新的方式）。[1002|1003|4000]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public Result update(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@PathVariable("id") @ApiParam(name = "id", required = true, value = "购物车ID") Long id, @ModelAttribute @ApiParam(name = "param", value = "加入购物车参数") ShoppingCartParam param) {
    	
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	ShoppingCartUpdateParam shoppingCartUpdateParam = new ShoppingCartUpdateParam();
    	shoppingCartUpdateParam.setProductModelId(param.getProductModelId());
    	shoppingCartUpdateParam.setQuantity(param.getQuantity());
    	
    	return successCreated(shoppingCartService.update(id, memberId, shoppingCartUpdateParam));
    }
    
    /**
     * 根据id删除购物车的商品。
     * 
     * @param id
     * @param token
     * @return
     */
    @ApiOperation(value = "删除购物车的商品", notes = "根据id删除购物车的商品。[1002|1003|4000]（蒋鑫俊）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public Result delete(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable(name = "id") Long id) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	Result result = successCreated(shoppingCartService.delete(id, memberId));
    	if (!isSuccess(result)) {
    		successCreated(result);
    	}
    	
    	return successDelete(result);
    }
    
}
