package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.framework.web.doc.annotation.Audit;
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
import com.lawu.eshop.member.api.service.PayOrderService;
import com.lawu.eshop.member.api.service.RechargeService;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.UserTypeEnum;
import com.lawu.eshop.property.dto.ThirdPayCallBackQueryPayOrderDTO;
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

	@Autowired
	private AlipayService alipayService;
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private PayOrderService payOrderService;

	@Audit(date = "2017-04-15", reviewer = "孙林青")
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
		aparam.setOutTradeNo(StringUtil.getRandomNum(""));
		aparam.setSubject(param.getThirdPayBodyEnum().val);
		aparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		aparam.setUserTypeEnum(UserTypeEnum.MEMBER);

		// 查询支付金额
		if (ThirdPartyBizFlagEnum.MEMBER_PAY_BILL.val.equals(param.getThirdPayBodyEnum().val)) {
			ThirdPayCallBackQueryPayOrderDTO payOrderCallback = payOrderService.selectThirdPayCallBackQueryPayOrder(param.getBizIds());
			aparam.setTotalAmount(String.valueOf(payOrderCallback.getActualMoney()));
			aparam.setSideUserNum(payOrderCallback.getBusinessUserNum());
			
		} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_ORDER.val.equals(param.getThirdPayBodyEnum().val)) {
			double orderMoney = shoppingOrderService.selectOrderMoney(param.getBizIds());
			aparam.setTotalAmount(String.valueOf(orderMoney));

		} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_BALANCE.val.equals(param.getThirdPayBodyEnum().val)
				|| ThirdPartyBizFlagEnum.MEMBER_PAY_POINT.val.equals(param.getThirdPayBodyEnum().val)) {
			double money = rechargeService.getRechargeMoney(param.getBizIds());
			aparam.setTotalAmount(String.valueOf(money));
		}

		return successGet(alipayService.getAppAlipayReqParams(aparam));

	}

}
