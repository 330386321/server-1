package com.lawu.eshop.property.constants;

/**
 * 商家交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MerchantTransactionTypeEnum {
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
	 * 销售商品收入
	 */
	REVENUE_FROM_SELLING_GOODS((byte)4);
	
	MerchantTransactionTypeEnum(Byte value){
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