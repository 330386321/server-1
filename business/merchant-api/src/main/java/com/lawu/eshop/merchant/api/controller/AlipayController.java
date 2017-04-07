package com.lawu.eshop.merchant.api.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.AlipayService;
import com.lawu.eshop.property.param.AppAlipayDataParam;
import com.lawu.eshop.property.param.PcAlipayDataParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 支付宝
 * </p>
 * @author Yangqh
 * @date 2017年4月7日 上午9:12:31
 *
 */
@Api(tags = "alipay")
@RestController
@RequestMapping(value = "alipay/")
public class AlipayController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(AlipayController.class);
	
	@Autowired
	private AlipayService alipayService;

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "app调用支付宝获取请求参数，已签名加密", notes = "app调用支付宝时需要的请求参数，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam AppAlipayDataParam param) {

		param.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		
		return successGet(alipayService.getAppAlipayReqParams(param));
		
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "PC端商家充值余额、积分、缴纳保证金接口", notes = "app调用支付宝时需要的请求参数，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "initPcPay", method = RequestMethod.POST)
	public void initPcPay(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam PcAlipayDataParam param) throws IOException {

		param.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		Result result = alipayService.initPcPay(param);
		if(ResultCode.SUCCESS == result.getRet()){
			Object obj = result.getModel();
			HttpServletResponse response = getResponse();
			PrintWriter out = response.getWriter();
			logger.info(obj.toString());
			out.println(obj.toString());
		}else{
			logger.error(result.getRet()+"-->"+result.getMsg());
		}
		
	}

}
