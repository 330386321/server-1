package com.lawu.eshop.operator.api.controller;

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
import com.lawu.eshop.mall.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;
import com.lawu.eshop.operator.api.service.ShoppingRefundDetailService;

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
	 * 同意退款给买家
	 * 
	 * @param id
	 *            退款详情id
	 * @param param
	 *            参数 是否同意申请
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@ApiOperation(value = "同意退款给买家", notes = "同意退款给买家。[1002|1003|4011|4013]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "agreeToRefund/{id}", method = RequestMethod.PUT)
    public Result agreeToRefund(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @PathVariable("id") @ApiParam(value = "退款详情id") Long id, @ModelAttribute @ApiParam(value = "同意退款参数") ShoppingRefundDetailAgreeToRefundForeignParam param) {
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
