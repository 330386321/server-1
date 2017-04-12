package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.property.param.BalancePayParam;

public interface BalancePayService {

	/**
	 * 订单余额支付
	 * 
	 * 1、校验接口参数规则
	 * 2、判断用户余额是否足够
	 * 3、
	 *
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	int orderPay(BalancePayParam param);

}
