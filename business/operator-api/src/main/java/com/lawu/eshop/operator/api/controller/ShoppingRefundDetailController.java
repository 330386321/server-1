package com.lawu.eshop.operator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.operator.api.service.ShoppingRefundDetailService;
import com.lawu.eshop.order.param.foreign.ShoppingRefundDetailAgreeToRefundForeignParam;

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
	 * 退款给买家
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@ApiOperation(value = "退款给买家", notes = "退款给买家。[1002|1003|4011|4013]（蒋鑫俊）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
	//@RequiresPermissions("shoppingRefundDetail:agreeToRefund")
    @RequestMapping(value = "agreeToRefund/{id}", method = RequestMethod.PUT)
    public Result agreeToRefund(@PathVariable("id") @ApiParam(value = "退款详情id") Long id) {
    	if (id == null || id <= 0) {
    		return successCreated(ResultCode.ID_EMPTY);
    	}
		
    	ShoppingRefundDetailAgreeToRefundForeignParam param = new ShoppingRefundDetailAgreeToRefundForeignParam();
    	param.setIsAgree(true);
    	
    	Result result = shoppingRefundDetailservice.agreeToRefund(id, param);
    	
    	if (!isSuccess(result)) {
    		return successGet(result.getRet());
    	}
		
    	return successCreated(result);
    }
	
	/**
	 * 买家撤销退款申请
	 * 
	 * @param id
	 *            退款详情id
	 * @return
	 */
	@ApiOperation(value = "撤销退款申请", notes = "买家撤销退款申请。[1002|1003|1004|4011|4014]（蒋鑫俊）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
	@SuppressWarnings("rawtypes")
	//@RequiresPermissions("shoppingRefundDetail:revokeRefundRequest")
	@RequestMapping(value = "revokeRefundRequest/{id}", method = RequestMethod.DELETE)
	public Result revokeRefundRequest(@PathVariable("id") Long id) {
		
		Result result = shoppingRefundDetailservice.revokeRefundRequest(id);
		
		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}
		
		return successDelete();
	}
}
