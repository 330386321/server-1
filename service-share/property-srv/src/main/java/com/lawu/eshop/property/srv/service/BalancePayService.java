package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.property.param.BalancePayDataParam;

public interface BalancePayService {

	/**
	 * 余额支付(商品订单、买单）
	 * 
	 * 1、校验接口参数规则 2、判断用户余额是否足够 3、新增y用户交易明细 4、减财产余额 *
	 * 
	 * @param param
	 * @return
	 */
	int balancePay(BalancePayDataParam param);

	/**
	 * 余额充值积分 //校验余额 //根据系统参数获取充值比例 //减财产余额、加财产积分 //新增余额充值积分交易明细 //新增积分交易明细
	 * 
	 * @param param
	 * @return
	 */
	int balancePayPoint(BalancePayDataParam param);

}
