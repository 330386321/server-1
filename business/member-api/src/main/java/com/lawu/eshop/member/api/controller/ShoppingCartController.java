package com.lawu.eshop.member.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.ShoppingCartDTO;
import com.lawu.eshop.mall.param.ShoppingCartParam;
import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.member.api.service.ProductModelService;
import com.lawu.eshop.member.api.service.ProductService;
import com.lawu.eshop.member.api.service.ShoppingCartService;
import com.lawu.eshop.product.dto.ProductImageDTO;
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
    private ProductService productService;
    
    @Autowired
    private MerchantStoreService merchantStoreService;
    
    /**
     * 加入购物车。
     * 
     * @param token
     * @param param
     * @return
     */
    @ApiOperation(value = "加入购物车", notes = "加入购物车。[1004|1005]（蒋鑫俊）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "save/{productModelId}", method = RequestMethod.POST)
    public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, 
    		@RequestParam("productModelId") @ApiParam(name = "productModelId", required = true, value = "商品型号ID") Long productModelId,
    		@RequestParam("quantity") @ApiParam(name = "quantity", required = true, value = "数量") Integer quantity) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	Result<ShoppingCartProductModelDTO> resultShoppingCartProductModelDTO = productModelService.getShoppingCartProductModel(productModelId);
    	
    	if (!isSuccess(resultShoppingCartProductModelDTO)) {
    		return successCreated(resultShoppingCartProductModelDTO.getRet());
    	}
    	
    	ShoppingCartProductModelDTO shoppingCartProductModelDTO = resultShoppingCartProductModelDTO.getModel();
    	
    	Result<String> resultMerchantName = merchantStoreService.getNameByMerchantId(shoppingCartProductModelDTO.getMerchantId());
    	
    	if (!isSuccess(resultMerchantName)) {
    		return successCreated(resultMerchantName.getRet());
    	}
    	
    	ShoppingCartParam param = new ShoppingCartParam();
    	param.setMerchantName(resultMerchantName.getModel());
    	param.setMerchantId(shoppingCartProductModelDTO.getMerchantId());
    	param.setProductId(shoppingCartProductModelDTO.getProductId());
    	param.setProductName(shoppingCartProductModelDTO.getProductName());
    	param.setProductModelId(shoppingCartProductModelDTO.getId());
    	param.setProductModelName(shoppingCartProductModelDTO.getName());
    	param.setQuantity(quantity);
    	param.setRegularPrice(shoppingCartProductModelDTO.getOriginalPrice());
    	param.setSalesPrice(shoppingCartProductModelDTO.getPrice());
    	
    	return successCreated(shoppingCartService.save(memberId, param));
    }

    /**
     * 根据memberId查询用户的购物车列表。
     * 
     * @param token
     * @return
     */
    @ApiOperation(value = "查询用户的购物车列表", notes = "根据memberId查询用户的购物车列表。[1004]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "findListByMemberId", method = RequestMethod.GET)
    Result<List<ShoppingCartDTO>> findListByMemberId(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	Result<List<ShoppingCartDTO>> resultShoppingCartDTOS = shoppingCartService.findListByMemberId(memberId);
    	
    	// 把要查询的id放入set,统一一次性查询
    	Set<Long> ids = new HashSet<Long>();
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		ids.add(shoppingCartDTO.getProductId());
    	}
    	
    	// 通过id list查询购物车内所有商品的图片，以id为key放入map
    	Result<List<ProductImageDTO>> resultProductImageDTOS = productService.getProductImage(new ArrayList<Long>(ids));
    	Map<Long, String> productImageMap = new HashMap<Long, String>();
    	if (resultProductImageDTOS.getModel() != null && !resultProductImageDTOS.getModel().isEmpty()) {
    		for (ProductImageDTO productImageDTO : resultProductImageDTOS.getModel()) {
    			if (productImageDTO != null && productImageDTO.getId() != null) {
    				productImageMap.put(productImageDTO.getId(), productImageDTO.getFeatureImage());
    			}
    		}
    	}
    	
    	// 从map获取商品图片
    	for (ShoppingCartDTO shoppingCartDTO : resultShoppingCartDTOS.getModel()) {
    		shoppingCartDTO.setFeatureImage(productImageMap.get(shoppingCartDTO.getProductId()));
    	}
    	
    	return successGet(resultShoppingCartDTOS);
    }
    
    /**
     * 根据id更新购物车的商品（使用实时更新不采用批量更新的方式）。
     * 
     * @param id
     * @param token
     * @param parm
     * @return
     */
    @ApiOperation(value = "更新购物车商品", notes = "根据id更新购物车的商品（使用实时更新不采用批量更新的方式）。[1002|1003|4000]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public Result update(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@PathVariable("id") @ApiParam(name = "id", required = true, value = "购物车ID") Long id, 
			@RequestParam("productModelId") @ApiParam(name = "productModelId", required = true, value = "商品型号ID") Long productModelId,
			@RequestParam("quantity") @ApiParam(name = "quantity", required = true, value = "数量") Integer quantity) {
    	
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	Result<ShoppingCartProductModelDTO> resultShoppingCartProductModelDTO = productModelService.getShoppingCartProductModel(productModelId);
    	
    	if (!isSuccess(resultShoppingCartProductModelDTO)) {
    		return successCreated(resultShoppingCartProductModelDTO.getRet());
    	}
    	
    	ShoppingCartProductModelDTO shoppingCartProductModelDTO = resultShoppingCartProductModelDTO.getModel();
    	
    	Result<String> resultMerchantName = merchantStoreService.getNameByMerchantId(shoppingCartProductModelDTO.getMerchantId());
    	
    	if (!isSuccess(resultMerchantName)) {
    		return successCreated(resultMerchantName.getRet());
    	}
    	
    	ShoppingCartParam param = new ShoppingCartParam();
    	param.setMerchantName(resultMerchantName.getModel());
    	param.setMerchantId(shoppingCartProductModelDTO.getMerchantId());
    	param.setProductId(shoppingCartProductModelDTO.getProductId());
    	param.setProductName(shoppingCartProductModelDTO.getProductName());
    	param.setProductModelId(shoppingCartProductModelDTO.getId());
    	param.setProductModelName(shoppingCartProductModelDTO.getName());
    	param.setQuantity(quantity);
    	param.setRegularPrice(shoppingCartProductModelDTO.getOriginalPrice());
    	param.setSalesPrice(shoppingCartProductModelDTO.getPrice());
    	
    	return successCreated(shoppingCartService.update(id, memberId, param));
    }
    
    /**
     * 根据id删除购物车的商品。
     * 
     * @param id
     * @param token
     * @return
     */
    @ApiOperation(value = "删除购物车的商品", notes = "根据id删除购物车的商品。[1002|1003|4000]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
	@RequestMapping(value = "delete/{id}", method = RequestMethod.PUT)
	public Result delete(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable(name = "id") Long id) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	return successCreated(shoppingCartService.delete(id, memberId));
    }
    
}
