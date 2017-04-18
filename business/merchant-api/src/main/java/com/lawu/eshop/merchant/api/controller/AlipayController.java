package com.lawu.eshop.merchant.api.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.lawu.eshop.framework.web.doc.annotation.Audit;
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
import com.lawu.eshop.merchant.api.service.PropertySrvPropertyService;
import com.lawu.eshop.merchant.api.service.RechargeService;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.param.PcAlipayDataParam;
import com.lawu.eshop.property.param.PcAlipayParam;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import com.lawu.eshop.property.param.ThirdPayParam;
import com.lawu.eshop.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 支付宝
 * </p>
 * 
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
	@Autowired
	private PropertySrvPropertyService propertyService;
	@Autowired
	private RechargeService rechargeService;

	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "app调用支付宝获取请求参数，已签名加密", notes = "app调用支付宝时需要的请求参数，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "getAppAlipayReqParams", method = RequestMethod.POST)
	public Result getAppAlipayReqParams(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam ThirdPayParam param) {

		ThirdPayDataParam aparam = new ThirdPayDataParam();
		aparam.setOutTradeNo(StringUtil.getRandomNum(""));
		aparam.setSubject(param.getThirdPayBodyEnum().val);
		aparam.setBizIds(param.getBizIds());
		aparam.setThirdPayBodyEnum(param.getThirdPayBodyEnum());
		aparam.setBizFlagEnum(param.getBizFlagEnum());
		aparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		aparam.setUserTypeEnum(UserTypeEnum.MEMCHANT);

		// 查询支付金额
		if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.val.equals(param.getBizFlagEnum().val)) {
			String bond = propertyService.getValue(PropertyType.MERCHANT_BONT);
			if ("".equals(bond)) {
				bond = PropertyType.MERCHANT_BONT_DEFAULT;
			}
			aparam.setTotalAmount(bond);
		} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.val.equals(param.getBizFlagEnum().val)
				|| ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.val.equals(param.getBizFlagEnum().val)) {
			double money = rechargeService.getRechargeMoney(param.getBizIds());
			if (money == 0) {
				return successCreated(ResultCode.MONEY_IS_ZERO);
			}
			aparam.setTotalAmount(String.valueOf(money));
		}

		return alipayService.getAppAlipayReqParams(aparam);

	}

	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "PC端商家充值余额、积分、缴纳保证金接口", notes = "app调用支付宝时需要的请求参数，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "initPcPay", method = RequestMethod.POST)
	public void initPcPay(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam PcAlipayParam param) throws IOException {

		PcAlipayDataParam aparam = new PcAlipayDataParam();
		aparam.setOutTradeNo(StringUtil.getRandomNum(""));
		aparam.setSubject(param.getSubject());
		aparam.setBizId(param.getBizId());
		aparam.setBizFlagEnum(param.getBizFlagEnum());
		aparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));

		// 查询支付金额
		if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.val.equals(param.getBizFlagEnum().val)) {
			String bond = propertyService.getValue(PropertyType.MERCHANT_BONT);
			if ("".equals(bond)) {
				bond = PropertyType.MERCHANT_BONT_DEFAULT;
			}
			aparam.setTotalAmount(bond);
		} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.val.equals(param.getBizFlagEnum().val)
				|| ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.val.equals(param.getBizFlagEnum().val)) {
			double money = rechargeService.getRechargeMoney(param.getBizId());
			aparam.setTotalAmount(String.valueOf(money));
		}

		Result result = alipayService.initPcPay(aparam);
		if (ResultCode.SUCCESS == result.getRet()) {
			Object obj = result.getModel();
			HttpServletResponse response = getResponse();
			PrintWriter out = response.getWriter();
			logger.info(obj.toString());
			out.println(obj.toString());
		} else {
			logger.error(result.getRet() + "-->" + result.getMsg());
		}

	}

}
