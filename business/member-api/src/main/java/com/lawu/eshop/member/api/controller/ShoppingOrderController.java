package com.lawu.eshop.member.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendDetailDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExtendQueryDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderItemDTO;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignParam;
import com.lawu.eshop.member.api.service.ProductModelService;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.product.param.ProductModeUpdateInventoryParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny 
 * @date 2017/04/06
 */
@Api(tags = "shoppingOrder")
@RestController
@RequestMapping(value = "shoppingOrder/")
public class ShoppingOrderController extends BaseController {

    @Autowired
    private ShoppingOrderService shoppingOrderService;
    
    @Autowired
    private ProductModelService productModelService;
    
    /**
     * 分页查询购物订单
     * 
     * @param token
     * @param param
     * @return
     */
    @ApiOperation(value = "分页查询购物订单", notes = "根据订单状态和是否评论分页查询购物订单。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public Result<Page<ShoppingOrderExtendQueryDTO>> page(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value = "购物订单查询参数") ShoppingOrderQueryForeignParam param) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	return successCreated(shoppingOrderService.selectPageByMemberId(memberId, param));
    }
    
    /**
     * 根据购物订单id查询购物订单详情
     * 
     * @param token
     * @param id
     * @return
     */
    @ApiOperation(value = "查询购物订单详情", notes = "根据购物订单id查询购物订单详情。[1002|1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public Result<ShoppingOrderExtendDetailDTO> get(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @RequestParam("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result<ShoppingOrderExtendDetailDTO> resultShoppingOrderExtendDetailDTO = shoppingOrderService.get(id);
    	
    	if (!isSuccess(resultShoppingOrderExtendDetailDTO)) {
    		return successGet(resultShoppingOrderExtendDetailDTO.getRet());
    	}
    	
    	return successCreated(resultShoppingOrderExtendDetailDTO);
    }
    
    /**
     * 查询物流动态
     * 
     * @param token
     * @param id
     * @return
     */
    @ApiOperation(value = "查询物流动态", notes = "查询物流动态。[1002|1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getExpressInfo/{id}", method = RequestMethod.GET)
    public Result<ShoppingOrderExpressDTO> expressInquiries(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result<ShoppingOrderExpressDTO> resultShoppingOrderExpressDTO = shoppingOrderService.getExpressInfo(id);
    	
    	if (!isSuccess(resultShoppingOrderExpressDTO)) {
    		return successGet(resultShoppingOrderExpressDTO.getRet());
    	}
    	
    	return successGet(resultShoppingOrderExpressDTO);
    }
    
    /**
     * 取消购物订单
     * 
     * @param token
     * @param id
     * @return
     */
    @ApiOperation(value = "取消购物订单", notes = "取消购物订单。[1002|1003|4002]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "cancelOrder/{id}", method = RequestMethod.PUT)
    public Result cancelOrder(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result resultShoppingOrderExpressDTO = shoppingOrderService.cancelOrder(id);
    	
    	if (!isSuccess(resultShoppingOrderExpressDTO)) {
    		return successCreated(resultShoppingOrderExpressDTO.getRet());
    	}
    	
    	// TODO 从商品模型表中释放库存(跨库事务通过事务补偿处理)
    	Result<ShoppingOrderExtendDetailDTO> resultShoppingOrderExtendDetailDTO = shoppingOrderService.get(id);
    	if (!isSuccess(resultShoppingOrderExtendDetailDTO)) {
    		return successCreated(resultShoppingOrderExtendDetailDTO.getRet());
    	}
    	
    	List<ProductModeUpdateInventoryParam> productModeUpdateInventoryParams = new ArrayList<ProductModeUpdateInventoryParam>();
    	for (ShoppingOrderItemDTO shoppingOrderItemDTO : resultShoppingOrderExtendDetailDTO.getModel().getItems()) {
    		ProductModeUpdateInventoryParam productModeUpdateInventoryParam = new ProductModeUpdateInventoryParam();
    		productModeUpdateInventoryParam.setId(shoppingOrderItemDTO.getProductModelId());
    		// 释放库存为负数
    		productModeUpdateInventoryParam.setQuantity(shoppingOrderItemDTO.getQuantity() * -1);
    		productModeUpdateInventoryParams.add(productModeUpdateInventoryParam);
    	}
    	
    	Result updateInventoryResult = productModelService.updateInventory(productModeUpdateInventoryParams);
    	if (!isSuccess(updateInventoryResult)) {
    		return successCreated(updateInventoryResult.getRet());
    	}
    	
    	return successCreated();
    }
}
