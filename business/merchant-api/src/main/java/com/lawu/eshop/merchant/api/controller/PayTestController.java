package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.PayTestService;
import com.lawu.eshop.property.param.AppAlipayDataParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 支付测试
 * </p>
 * @author Yangqh
 * @date 2017年4月7日 上午9:12:31
 *
 */
@Api(tags = "payTest")
@RestController
@RequestMapping(value = "pay/")
public class PayTestController extends BaseController {

	@Autowired
	private PayTestService payTestService;

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "支付宝获取请求参数，已处理", notes = "app调用支付宝时需要的请求参数，[]，(杨清华)", httpMethod = "POST")
//	@Authorization
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam AppAlipayDataParam param) {

		param.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		
		return successGet(payTestService.getAppAlipayReqParams(param));
		
	}

}
