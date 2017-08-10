package com.lawu.eshop.member.api.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.lawu.eshop.member.api.service.MerchantStoreService;
import com.lawu.eshop.user.dto.VisitUserInfoDTO;
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
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageTempParam;
import com.lawu.eshop.member.api.service.BalancePayService;
import com.lawu.eshop.member.api.service.MessageService;
import com.lawu.eshop.member.api.service.PayOrderService;
import com.lawu.eshop.member.api.service.PropertyInfoService;
import com.lawu.eshop.member.api.service.RechargeService;
import com.lawu.eshop.member.api.service.ShoppingOrderService;
import com.lawu.eshop.order.constants.PayOrderStatusEnum;
import com.lawu.eshop.order.dto.ShoppingOrderMoneyDTO;
import com.lawu.eshop.order.dto.ThirdPayCallBackQueryPayOrderDTO;
import com.lawu.eshop.property.dto.PropertyPointAndBalanceDTO;
import com.lawu.eshop.property.param.BalancePayDataParam;
import com.lawu.eshop.property.param.BalancePayParam;
import com.lawu.eshop.user.constants.UserCommonConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 余额支付
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月11日 下午8:18:54
 *
 */
@Api(tags = "balancePay")
@RestController
@RequestMapping(value = "balancePay/")
public class BalancePayController extends BaseController {

	@Autowired
	private BalancePayService balancePayService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private PropertyInfoService propertyInfoService;
	@Autowired
	private MerchantStoreService merchantStoreService;

	/**
	 * 余额支付订单
	 * 
	 * @param param
	 * @return
	 */
	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@Authorization
	@ApiOperation(value = "商品订单余额支付", notes = "商品订单余额支付,[4020|4021]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "orderPay", method = RequestMethod.POST)
	public Result orderPay(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam BalancePayParam param) {
		BalancePayDataParam dparam = new BalancePayDataParam();
		dparam.setBizIds(param.getBizIds());
		dparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		dparam.setAccount(UserUtil.getCurrentAccount(getRequest()));
		/*
		 *  获取订单金额
		 *  考虑商品可能有减库存失败可能
		 */
		Result<ShoppingOrderMoneyDTO> result = shoppingOrderService.selectOrderMoney(param.getBizIds());
		if (!isSuccess(result)) {
			return successCreated(result.getRet());
		}
		double orderMoney = result.getModel().getOrderTotalPrice().doubleValue();
		dparam.setTotalAmount(String.valueOf(orderMoney));

		return balancePayService.orderPay(dparam);
	}

	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@Authorization
	@ApiOperation(value = "买单余额支付", notes = "买单余额支付,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "billPay", method = RequestMethod.POST)
	public Result billPay(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam BalancePayParam param) {
		BalancePayDataParam dparam = new BalancePayDataParam();
		dparam.setBizIds(param.getBizIds());
		dparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		dparam.setAccount(UserUtil.getCurrentAccount(getRequest()));
		ThirdPayCallBackQueryPayOrderDTO payOrderCallback = payOrderService
				.selectThirdPayCallBackQueryPayOrder(param.getBizIds());
		if (payOrderCallback == null) {
			return successCreated(ResultCode.PAY_ORDER_NULL);
		} else if (PayOrderStatusEnum.STATUS_PAY_SUCCESS.getVal().equals(payOrderCallback.getPayOrderStatusEnum().getVal())) {
			return successCreated(ResultCode.PAY_ORDER_IS_SUCCESS);
		}
		dparam.setTotalAmount(String.valueOf(payOrderCallback.getActualMoney()));
		dparam.setSideUserNum(payOrderCallback.getBusinessUserNum());
		dparam.setOrderNum(payOrderCallback.getOrderNum());

		VisitUserInfoDTO visitUserInfoDTO = merchantStoreService.findAccountAndRegionPathByNum(payOrderCallback.getBusinessUserNum());
		dparam.setRegionPath(visitUserInfoDTO.getRegionPath());

		return balancePayService.billPay(dparam);
	}

	@Audit(date = "2017-04-15", reviewer = "孙林青")
	@SuppressWarnings("rawtypes")
	@Authorization
	@ApiOperation(value = "余额充值积分", notes = "余额充值积分,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "balancePayPoint", method = RequestMethod.POST)
	public Result balancePayPoint(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam BalancePayParam param) {
		String userNum = UserUtil.getCurrentUserNum(getRequest());
		BalancePayDataParam dparam = new BalancePayDataParam();
		dparam.setBizIds(param.getBizIds());
		dparam.setUserNum(userNum);
		dparam.setAccount(UserUtil.getCurrentAccount(getRequest()));

		ThirdPayCallBackQueryPayOrderDTO payOrderCallback = rechargeService.getRechargeMoney(param.getBizIds());
		dparam.setTotalAmount(String.valueOf(payOrderCallback.getActualMoney()));
		dparam.setOrderNum(payOrderCallback.getOrderNum());
		
		Result result = balancePayService.balancePayPoint(dparam);
		if(ResultCode.SUCCESS != result.getRet()){
			return result;
		}
		
		// ------------------------------发送站内消息
		DecimalFormat df = new DecimalFormat("######0.00");
		MessageInfoParam messageInfoParam = new MessageInfoParam();
		messageInfoParam.setRelateId(0L);
		messageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_RECHARGE_POINT);
		MessageTempParam messageTempParam = new MessageTempParam();
		messageTempParam.setRechargeBalance(new BigDecimal(df.format(payOrderCallback.getActualMoney())));
		Result<PropertyPointAndBalanceDTO> moneyResult = propertyInfoService.getPropertyInfoMoney(userNum);
		messageTempParam.setPoint(moneyResult.getModel().getPoint().setScale(2, BigDecimal.ROUND_HALF_UP));
		if (userNum.startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
			messageTempParam.setUserName("E店会员");
		} else if (userNum.startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
			messageTempParam.setUserName("E店商家");
		}
		messageInfoParam.setMessageParam(messageTempParam);
		messageService.saveMessage(userNum, messageInfoParam);
		// ------------------------------发送站内消息

		return successCreated();
	}
}
