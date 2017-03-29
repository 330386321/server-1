package com.lawu.eshop.property.constants;

/**
 * 会员交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MemberTransactionTypeEnum {
	
	/**
	 * 充值余额
	 */
	RECHARGE_THE_BALANCE((byte)1),
	
	/**
	 * 提现
	 */
	WITHDRAW((byte)2),
	
	/**
	 * 提现失败
	 */
	WITHDRAW_FAILURE((byte)3),
	
	/**
	 * 支付商品订单
	 */
	PAY_FOR_MERCHANDISE_ORDERS((byte)4);
	
	
	MemberTransactionTypeEnum(Byte value){
		this.value = value;
	}
	
	private Byte value;

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}
	
}