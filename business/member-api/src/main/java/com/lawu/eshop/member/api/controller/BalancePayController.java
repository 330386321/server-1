package com.lawu.eshop.member.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.lawu.eshop.member.api.service.BalancePayService;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitle;
import com.lawu.eshop.property.param.BalancePayDataParam;
import com.lawu.eshop.property.param.BalancePayParam;

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

	/**
	 * 余额支付订单
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Authorization
	@ApiOperation(value = "商品订单余额支付", notes = "商品订单余额支付,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "orderPay", method = RequestMethod.POST)
	public Result orderPay(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam BalancePayParam param) {
		BalancePayDataParam dparam = new BalancePayDataParam();
		dparam.setAmount(param.getAmount());
		dparam.setBizIds(param.getBizIds());
		dparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		dparam.setAccount(UserUtil.getCurrentAccount(getRequest()));
		return balancePayService.orderPay(dparam);
	}

	@SuppressWarnings("rawtypes")
	@Authorization
	@ApiOperation(value = "买单余额支付", notes = "买单余额支付,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "billPay", method = RequestMethod.POST)
	public Result billPay(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam BalancePayParam param) {
		BalancePayDataParam dparam = new BalancePayDataParam();
		dparam.setAmount(param.getAmount());
		dparam.setBizIds(param.getBizIds());
		dparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		dparam.setAccount(UserUtil.getCurrentAccount(getRequest()));
		return balancePayService.billPay(dparam);
	}

	@SuppressWarnings("rawtypes")
	// @Authorization
	@ApiOperation(value = "余额充值积分", notes = "余额充值积分,[]（杨清华）", httpMethod = "POST")
	@RequestMapping(value = "balancePayPoint", method = RequestMethod.POST)
	public Result balancePayPoint(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam BalancePayParam param) {
		BalancePayDataParam dparam = new BalancePayDataParam();
		dparam.setAmount(param.getAmount());
		dparam.setBizIds(param.getBizIds());
		dparam.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
		dparam.setAccount(UserUtil.getCurrentAccount(getRequest()));
		return balancePayService.balancePayPoint(dparam);
	}
}
