package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.param.BalancePayParam;

@FeignClient(value = "property-srv")
public interface BalancePayService {

	/**
	 * 余额充值积分
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "balancePay/balancePayPoint")
	Result balancePayPoint(BalancePayParam param);

}
