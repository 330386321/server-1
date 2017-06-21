package com.lawu.eshop.member.api.controller;

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
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.member.api.service.AlipayService;
import com.lawu.eshop.member.api.service.PayOrderService;
import com.lawu.eshop.member.api.service.RechargeService;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.order.constants.PayOrderStatusEnum;
import com.lawu.eshop.order.dto.ShoppingOrderMoneyDTO;
import com.lawu.eshop.order.dto.ThirdPayCallBackQueryPayOrderDTO;
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
	@ApiOperation(value = "app调用支付宝获取请求参数，已签名加密", notes = "app调用支付宝时需要的请求参数，[4020|4021]，(杨清华)", httpMethod = "POST")
	@Authorization
	@RequestMapping(value = "getAppAlipayReqParams", method = RequestMethod.POST)
	public Result getAppAlipayReqParams(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
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
		double money = 0;
		if (ThirdPartyBizFlagEnum.MEMBER_PAY_BILL.val.equals(param.getBizFlagEnum().val)) {
			ThirdPayCallBackQueryPayOrderDTO payOrderCallback = payOrderService
					.selectThirdPayCallBackQueryPayOrder(param.getBizIds());
			if(payOrderCallback == null){
				return successCreated(ResultCode.PAY_ORDER_NULL);
			}else if(PayOrderStatusEnum.STATUS_PAY_SUCCESS.val.equals(payOrderCallback.getPayOrderStatusEnum().val)){
				return successCreated(ResultCode.PAY_ORDER_IS_SUCCESS);
			}
			aparam.setSideUserNum(payOrderCallback.getBusinessUserNum());
			money = payOrderCallback.getActualMoney();

		} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_ORDER.val.equals(param.getBizFlagEnum().val)) {
			// 考虑商品可能有减库存失败可能
			Result<ShoppingOrderMoneyDTO> result = shoppingOrderService.selectOrderMoney(param.getBizIds());
			if (!isSuccess(result)) {
				return successCreated(result.getRet());
			}
			money = result.getModel().getOrderTotalPrice().doubleValue();
		} else if (ThirdPartyBizFlagEnum.MEMBER_PAY_BALANCE.val.equals(param.getBizFlagEnum().val)
				|| ThirdPartyBizFlagEnum.MEMBER_PAY_POINT.val.equals(param.getBizFlagEnum().val)) {
			ThirdPayCallBackQueryPayOrderDTO recharge = rechargeService.getRechargeMoney(param.getBizIds());
			money = recharge.getActualMoney();
			
		}
		if(StringUtil.doubleCompareTo(money, 0) == 0){
			return successCreated(ResultCode.MONEY_IS_ZERO);
		}
		aparam.setTotalAmount(String.valueOf(money));
		
		return successCreated(alipayService.getAppAlipayReqParams(aparam));

	}

}
