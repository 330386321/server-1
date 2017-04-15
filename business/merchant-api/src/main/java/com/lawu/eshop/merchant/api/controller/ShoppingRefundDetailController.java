package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.mall.dto.foreign.ShoppingRefundDetailDTO;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundDetailRerurnAddressForeignParam;
import com.lawu.eshop.merchant.api.service.ShoppingRefundDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny 
 * @date 2017/04/06
 */
@Api(tags = "shoppingRefundDetail")
@RestController
@RequestMapping(value = "shoppingRefundDetail/")
public class ShoppingRefundDetailController extends BaseController {

	@Autowired
	private ShoppingRefundDetailService shoppingRefundDetailservice;
	
	
	/**
	 * 根据id查询退款详情的物流信息
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询退货的物流信息", notes = "查询退货的物流信息。[1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getExpressInfo/{id}", method = RequestMethod.GET)
    public Result<ShoppingOrderExpressDTO> getExpressInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "退款详情id") Long id) {
    	if (id == null || id <= 0) {
    		return successCreated(ResultCode.ID_EMPTY);
    	}
		
    	Result<ShoppingOrderExpressDTO> resultShoppingOrderExpressDTO = shoppingRefundDetailservice.getExpressInfo(id);
    	
    	if (!isSuccess(resultShoppingOrderExpressDTO)) {
    		return successCreated(resultShoppingOrderExpressDTO.getRet());
    	}
		
    	return successCreated(resultShoppingOrderExpressDTO);
    }
	
	/**
	 * 根据购物订单项id查询退款详情
	 * 
	 * @param shoppingOrderItemId
	 *            购物订单项id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询退款详情", notes = "根据购物订单项id查询退款详情。[1002|1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getRefundDetail/{shoppingOrderItemId}", method = RequestMethod.GET)
    public Result<ShoppingOrderExpressDTO> getRefundDetail(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("shoppingOrderItemId") @ApiParam(name = "shoppingOrderItemId", value = "购物订单项id") Long shoppingOrderItemId) {
    	if (shoppingOrderItemId == null || shoppingOrderItemId <= 0) {
    		return successCreated(ResultCode.ID_EMPTY);
    	}
		
    	Result<ShoppingRefundDetailDTO> resultShoppingRefundDetailDTO = shoppingRefundDetailservice.getRefundDetail(shoppingOrderItemId);
    	
    	if (!isSuccess(resultShoppingRefundDetailDTO)) {
    		return successGet(resultShoppingRefundDetailDTO.getRet());
    	}
		
    	return successCreated(resultShoppingRefundDetailDTO);
    }
	
	/**
	 * 商家是否同意买家的退货申请
	 * 
	 * @param id
	 *            退款详情id
	 * @param param
	 *            参数 是否同意申请
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "商家确认买家的退货申请", notes = "商家是否同意买家的退货申请。[1002|1003|1004|4009|4011]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "agreeToApply/{id}", method = RequestMethod.PUT)
    public Result<ShoppingOrderExpressDTO> agreeToApply(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(value = "退款详情id") Long id, @ModelAttribute @ApiParam(value = "申请审核参数") ShoppingRefundDetailAgreeToApplyForeignParam param) {
    	if (id == null || id <= 0) {
    		return successCreated(ResultCode.ID_EMPTY);
    	}
		
    	Result result = shoppingRefundDetailservice.agreeToApply(id, param);
    	
    	if (!isSuccess(result)) {
    		return successGet(result.getRet());
    	}
		
    	return successCreated(result);
    }
	
	/**
	 * 商家填写退货地址 
	 * 根据退款详情id更新退货地址
	 * 
	 * @param id
	 *            退款详情id
	 * @param param
	 *            参数 退货信息参数
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "商家填写退货地址 ", notes = "商家填写退货地址 。[1002|1003|4011|4012]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "fillReturnAddress/{id}", method = RequestMethod.PUT)
    public Result<ShoppingOrderExpressDTO> fillReturnAddress(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(value = "退款详情id") Long id, @ModelAttribute @ApiParam(value = "退货地址参数") ShoppingRefundDetailRerurnAddressForeignParam param) {
    	if (id == null || id <= 0) {
    		return successCreated(ResultCode.ID_EMPTY);
    	}
		
    	Result result = shoppingRefundDetailservice.fillReturnAddress(id, param);
    	
    	if (!isSuccess(result)) {
    		return successGet(result.getRet());
    	}
		
    	return successCreated(result);
    }
	
	/**
	 * 商家是否同意退款
	 * 
	 * @param id
	 *            退款详情id
	 * @param param
	 *            参数 是否同意申请
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "商家是否同意退款", notes = "商家是否同意退款。[1002|1003|4011|4013]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "agreeToRefund/{id}", method = RequestMethod.PUT)
    public Result<ShoppingOrderExpressDTO> agreeToRefund(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(value = "退款详情id") Long id, @ModelAttribute @ApiParam(value = "同意退款参数") ShoppingRefundDetailAgreeToRefundForeignParam param) {
    	if (id == null || id <= 0) {
    		return successCreated(ResultCode.ID_EMPTY);
    	}
		
    	Result result = shoppingRefundDetailservice.agreeToRefund(id, param);
    	
    	if (!isSuccess(result)) {
    		return successGet(result.getRet());
    	}
		
    	return successCreated(result);
    }
}
