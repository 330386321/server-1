package com.lawu.eshop.member.api.controller;

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
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.AlipayService;
import com.lawu.eshop.member.api.service.OrderService;
import com.lawu.eshop.member.api.service.PropertySrvPropertyService;
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
	private OrderService orderService;
	@Autowired
	private PropertySrvPropertyService propertyService;

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "app调用支付宝获取请求参数，已签名加密", notes = "app调用支付宝时需要的请求参数，[]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam ThirdPayParam param) {

		ThirdPayDataParam aparam = new ThirdPayDataParam();
		aparam.setBizIds(param.getBizIds());
		aparam.setThirdPayBodyEnum(param.getThirdPayBodyEnum());
		aparam.setBizFlagEnum(param.getBizFlagEnum());
		aparam.setTotalAmount(param.getTotalAmount());
		aparam.setOutTradeNo(StringUtil.getRandomNum(""));
		aparam.setSubject(param.getThirdPayBodyEnum().val);
		aparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		aparam.setUserTypeEnum(UserTypeEnum.MEMBER);

		// 查询支付金额
		if (ThirdPartyBizFlagEnum.MEMBER_PAY_BILL.val.equals(param.getThirdPayBodyEnum().val)) {
			aparam.setTotalAmount(param.getTotalAmount());

		} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_ORDER.val.equals(param.getThirdPayBodyEnum().val)) {
			double orderMoney = orderService.selectOrderMoney(param.getBizIds());
			aparam.setTotalAmount(String.valueOf(orderMoney));

		} else if (ThirdPartyBizFlagEnum.BUSINESS_PAY_BOND.val.equals(param.getThirdPayBodyEnum().val)) {
			String bond = propertyService.getValue(PropertyType.MERCHANT_BONT);
			if ("".equals(bond)) {
				bond = PropertyType.MERCHANT_BONT_DEFAULT;
			}
			aparam.setTotalAmount(bond);
		}

		return successGet(alipayService.getAppAlipayReqParams(aparam));

	}

}
