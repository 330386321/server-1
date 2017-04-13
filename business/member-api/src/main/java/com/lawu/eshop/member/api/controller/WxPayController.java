package com.lawu.eshop.member.api.controller;

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
import com.lawu.eshop.member.api.service.OrderService;
import com.lawu.eshop.member.api.service.PropertySrvPropertyService;
import com.lawu.eshop.member.api.service.RechargeService;
import com.lawu.eshop.member.api.service.WxPayService;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import com.lawu.eshop.property.param.ThirdPayParam;
import com.lawu.eshop.utils.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 微信
 * </p>
 * 
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
	@Autowired
	private OrderService orderService;
	@Autowired
	private PropertySrvPropertyService propertyService;
	@Autowired
	private RechargeService rechargeService;

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "app调用微信生成预支付订单返回签名加密参数", notes = "app调用微信生成预支付订单返回签名加密参数，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "getPrepayInfo", method = RequestMethod.POST)
	public Result getPrepayInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam ThirdPayParam param) {

		ThirdPayDataParam aparam = new ThirdPayDataParam();
		aparam.setOutTradeNo(StringUtil.getRandomNum(""));
		aparam.setThirdPayBodyEnum(param.getThirdPayBodyEnum());
		aparam.setSubject(param.getThirdPayBodyEnum().val);
		aparam.setBizIds(param.getBizIds());
		aparam.setBizFlagEnum(param.getBizFlagEnum());
		aparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		aparam.setUserTypeEnum(UserTypeEnum.MEMCHANT);

		// 查询支付金额
		if (ThirdPartyBizFlagEnum.MEMBER_PAY_BILL.val.equals(param.getThirdPayBodyEnum().val)) {
			aparam.setTotalAmount(param.getTotalAmount());// 买单需要app传入

		} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_ORDER.val.equals(param.getThirdPayBodyEnum().val)) {
			double orderMoney = orderService.selectOrderMoney(param.getBizIds());
			aparam.setTotalAmount(String.valueOf(orderMoney));

		} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.val.equals(param.getThirdPayBodyEnum().val)) {
			String bond = propertyService.getValue(PropertyType.MERCHANT_BONT);
			if ("".equals(bond)) {
				bond = PropertyType.MERCHANT_BONT_DEFAULT;
			}
			aparam.setTotalAmount(bond);
		} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BALANCE.val.equals(param.getThirdPayBodyEnum().val)
				|| ThirdPartyBizFlagEnum.BUSINESS_PAY_POINT.val.equals(param.getThirdPayBodyEnum().val)
				|| ThirdPartyBizFlagEnum.MEMBER_PAY_BALANCE.val.equals(param.getThirdPayBodyEnum().val)
				|| ThirdPartyBizFlagEnum.MEMBER_PAY_POINT.val.equals(param.getThirdPayBodyEnum().val)) {
			double money = rechargeService.getRechargeMoney(param.getBizIds());
			aparam.setTotalAmount(String.valueOf(money));
		}

		return wxPayService.getPrepayInfo(aparam);

	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "PC端商家充值余额、积分、缴纳保证金接口返回扫码支付二维码", notes = "PC端商家充值余额、积分、缴纳保证金接口返回扫码支付二维码，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "initPcPay", method = RequestMethod.POST)
	public Result initPcPay(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam ThirdPayParam param) throws IOException {

		ThirdPayDataParam aparam = new ThirdPayDataParam();
		aparam.setTotalAmount(param.getTotalAmount());
		aparam.setOutTradeNo(StringUtil.getRandomNum(""));
		aparam.setThirdPayBodyEnum(param.getThirdPayBodyEnum());
		aparam.setSubject(param.getThirdPayBodyEnum().val);
		aparam.setBizIds(param.getBizIds());
		aparam.setBizFlagEnum(param.getBizFlagEnum());
		aparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		aparam.setUserTypeEnum(UserTypeEnum.MEMCHANT_PC);

		return wxPayService.getPrepayInfo(aparam);
	}

}
