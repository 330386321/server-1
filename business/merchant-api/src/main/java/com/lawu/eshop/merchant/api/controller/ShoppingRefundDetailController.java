package com.lawu.eshop.merchant.api.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.merchant.api.service.AddressService;
import com.lawu.eshop.merchant.api.service.ShoppingRefundDetailService;
import com.lawu.eshop.order.dto.foreign.ShoppingOrderExpressDTO;
import com.lawu.eshop.order.dto.foreign.ShoppingRefundDetailDTO;
import com.lawu.eshop.order.param.ShoppingRefundDetailRerurnAddressParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToApplyForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailRerurnAddressForeignParam;
import com.lawu.eshop.user.dto.AddressDTO;

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
	private ShoppingRefundDetailService shoppingRefundDetailService;
	
	@Autowired
	private AddressService addressService;
	
	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询退货的物流信息", notes = "查询退货的物流信息。[1003]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getExpressInfo/{id}", method = RequestMethod.GET)
    public Result<ShoppingOrderExpressDTO> getExpressInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(name = "id", value = "退款详情id") Long id) {
    	if (id == null || id <= 0) {
    		return successCreated(ResultCode.ID_EMPTY);
    	}
		
    	Result<ShoppingOrderExpressDTO> resultShoppingOrderExpressDTO = shoppingRefundDetailService.getExpressInfo(id);
    	
    	if (!isSuccess(resultShoppingOrderExpressDTO)) {
    		return successCreated(resultShoppingOrderExpressDTO.getRet());
    	}
		
    	return successCreated(resultShoppingOrderExpressDTO);
    }
	
	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "查询退款详情", notes = "根据购物订单项id查询退款详情。[1100|1024]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "getRefundDetail/{shoppingOrderItemId}", method = RequestMethod.GET)
    public Result<ShoppingRefundDetailDTO> getRefundDetail(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("shoppingOrderItemId") @ApiParam(name = "shoppingOrderItemId", value = "购物订单项id") Long shoppingOrderItemId) {
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
		Result<ShoppingRefundDetailDTO> result = shoppingRefundDetailService.getRefundDetail(shoppingOrderItemId, merchantId);
    	return successCreated(result);
    }
	
	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "商家确认买家的退货申请", notes = "商家是否同意买家的退货申请。[1004|1100|1024|4027]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "agreeToApply/{id}", method = RequestMethod.PUT)
    public Result<ShoppingOrderExpressDTO> agreeToApply(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(value = "退款详情id") @Validated @NotNull Long id, @ModelAttribute @ApiParam(value = "申请审核参数") @Validated ShoppingRefundDetailAgreeToApplyForeignParam param, BindingResult bindingResult) {
		String message = validate(bindingResult);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	Result result = shoppingRefundDetailService.agreeToApply(id, merchantId, param);
    	return successCreated(result);
    }
	
	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "商家填写退货地址 ", notes = "商家填写退货地址 。[1004|1100|1024|4029]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "fillReturnAddress/{id}", method = RequestMethod.PUT)
    public Result<ShoppingOrderExpressDTO> fillReturnAddress(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(value = "退款详情id", required = true) Long id, @ModelAttribute @ApiParam(value = "退货地址参数") ShoppingRefundDetailRerurnAddressForeignParam foreignParam, BindingResult bindingResult) {
		String message = validate(bindingResult);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
		ShoppingRefundDetailRerurnAddressParam param = new ShoppingRefundDetailRerurnAddressParam();
    	if (foreignParam.getAddressId() != null && foreignParam.getAddressId() > 0) {
    		Result<AddressDTO> getResult = addressService.get(foreignParam.getAddressId());
    		if (!isSuccess(getResult)) {
    			return successCreated(getResult);
    		}
    		param.setConsigneeName(getResult.getModel().getName());
    		param.setConsigneeMobile(getResult.getModel().getMobile());
    		param.setConsigneeAddress(getResult.getModel().getRegionName() + getResult.getModel().getAddr());
    	}
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	Result result = shoppingRefundDetailService.fillReturnAddress(id, merchantId, param);
    	return successCreated(result);
    }
	
	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings({ "rawtypes" })
	@ApiOperation(value = "商家是否同意退款", notes = "商家是否同意退款。[1002|1003|4011|4013]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "agreeToRefund/{id}", method = RequestMethod.PUT)
    public Result agreeToRefund(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(value = "退款详情id", required = true) Long id, @ModelAttribute @ApiParam(value = "同意退款参数") @Validated ShoppingRefundDetailAgreeToRefundForeignParam param, BindingResult bindingResult) {
		String message = validate(bindingResult);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
    	Long merchantId = UserUtil.getCurrentUserId(getRequest());
    	Result result = shoppingRefundDetailService.agreeToRefund(id, merchantId, param);
    	return successCreated(result);
    }
}
