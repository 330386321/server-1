package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.param.BalancePayDataParam;
import com.lawu.eshop.property.param.BalancePayParam;

@FeignClient(value = "property-srv")
public interface BalancePayService {

	/**
	 * 订单余额支付
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "balancePay/orderPay")
	Result orderPay(@RequestBody BalancePayDataParam param);

	/**
	 * 买单余额支付
	 * @param dparam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "balancePay/billPay")
	Result billPay(BalancePayDataParam dparam);

	/**
	 * 余额充值积分
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "balancePay/balancePayPoint")
	Result balancePayPoint(BalancePayParam param);

}
