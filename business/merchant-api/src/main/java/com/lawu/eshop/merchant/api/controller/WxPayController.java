package com.lawu.eshop.merchant.api.controller;

import java.io.IOException;

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
import com.lawu.eshop.merchant.api.service.WxPayService;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.param.AppAlipayDataParam;
import com.lawu.eshop.property.param.AppAlipayParam;
import com.lawu.eshop.property.param.PcAlipayParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 微信
 * </p>
 * @author Yangqh
 * @date 2017年4月7日 下午9:01:21
 *
 */
@Api(tags = "wxPay")
@RestController
@RequestMapping(value = "wxPay/")
public class WxPayController extends BaseController {

	@Autowired
	private WxPayService wxPayService;

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "app调用微信生成预支付订单返回签名加密参数", notes = "app调用微信生成预支付订单返回签名加密参数，[]，(杨清华)", httpMethod = "POST")
//	@Authorization
	@RequestMapping(value = "getPrepayInfo", method = RequestMethod.POST)
	public Result getPrepayInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam AppAlipayParam param) {

		AppAlipayDataParam aparam = new AppAlipayDataParam();
		aparam.setTotalAmount(param.getTotalAmount());
		aparam.setOutTradeNo(param.getOutTradeNo());
		aparam.setSubject(param.getSubject());
		aparam.setBizIds(param.getBizIds());
		aparam.setBody(param.getBody());
		aparam.setBizFlagEnum(param.getBizFlagEnum());
		aparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		aparam.setUserTypeEnum(UserTypeEnum.MEMCHANT);
		
		return successGet(wxPayService.getPrepayInfo(aparam));
		
	}
	
	@ApiOperation(value = "PC端商家充值余额、积分、缴纳保证金接口", notes = "app调用支付宝时需要的请求参数，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "initPcPay", method = RequestMethod.POST)
	public void initPcPay(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam PcAlipayParam param) throws IOException {

		
	}

}