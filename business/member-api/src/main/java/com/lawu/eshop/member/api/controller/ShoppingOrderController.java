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
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderItemRefundDTO;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderRequestRefundForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundQueryForeignParam;
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
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "分页查询购物订单", notes = "根据订单状态和是否评论分页查询购物订单。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "selectPageByMemberId", method = RequestMethod.GET)
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("rawtypes")
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
    
	/**
	 * 删除购物订单
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "删除购物订单", notes = "根据购物订单id删除购物订单。[1002|1003|4003]（蒋鑫俊）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
    @RequestMapping(value = "deleteOrder/{id}", method = RequestMethod.DELETE)
    public Result deleteOrder(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result result = shoppingOrderService.deleteOrder(id);
    	
    	if (!isSuccess(result)) {
    		return successCreated(result.getRet());
    	}
    	return successDelete();
    }
    
	/**
	 * 支付成功之后
	 * 回调修改购物订单以及订单项状态为待发货
	 * 提供给api接口调用，也可以在api内部调用
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "支付成功", notes = "支付成功之后的回调方法。[1002|1003|4004]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "paymentSuccessful/{id}", method = RequestMethod.PUT)
    public Result paymentSuccessful(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result result = shoppingOrderService.paymentSuccessful(id);
    	
    	if (!isSuccess(result)) {
    		return successCreated(result.getRet());
    	}
    	return successCreated();
    }
    
	/**
	 * 确认收货之后
	 * 修改购物订单以及订单项状态为交易成功
	 * 
	 * @param id
	 *            购物订单id
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "确认收货", notes = "根据购物订单id确认收货。[1002|1003|4005]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "tradingSuccess/{id}", method = RequestMethod.PUT)
    public Result tradingSuccess(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "购物订单id") Long id) {
    	
    	Result result = shoppingOrderService.tradingSuccess(id);
    	
    	if (!isSuccess(result)) {
    		return successCreated(result.getRet());
    	}
    	return successCreated();
    }
    
	/**
	 * 买家申请退款
	 * 修改订单状态为待商家确认
	 * 
	 * @param shoppingOrderitemId 购物订单项id
	 * @param param 退款参数
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "申请退款", notes = "根据购物订单项id申请退款。[1002|1003|4005]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(value = "requestRefund/{shoppingOrderitemId}", method = RequestMethod.PUT)
    public Result requestRefund(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
    		@PathVariable("shoppingOrderitemId") @ApiParam(name = "shoppingOrderitemId", value = "购物订单项id") Long shoppingOrderitemId,
    		@ModelAttribute @ApiParam(name = "param", value="退款参数") ShoppingOrderRequestRefundForeignParam param) {
    	
    	Result result = shoppingOrderService.requestRefund(shoppingOrderitemId, param);
    	
    	if (!isSuccess(result)) {
    		return successCreated(result.getRet());
    	}
    	return successCreated();
    }
    
    /**
	 * 根据查询参数分页查询退款记录
	 * 购物订单 购物订单项 退款详情关联查询
	 * 
	 * @param memberId
	 *            会员id
	 * @param param
	 *            查询参数
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "分页查询退款记录", notes = "分页查询退款记录。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "selectRefundPageByMemberId", method = RequestMethod.GET)
    public Result requestRefund(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(name = "param", value="查询参数") ShoppingRefundQueryForeignParam param) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	
    	Result<Page<ShoppingOrderItemRefundDTO>> result = shoppingOrderService.selectRefundPageByMemberId(memberId, param);
    	
    	if (!isSuccess(result)) {
    		return successGet(result.getRet());
    	}
    	return successGet(result);
    }
}
