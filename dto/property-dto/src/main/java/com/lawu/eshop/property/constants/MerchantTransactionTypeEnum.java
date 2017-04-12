package com.lawu.eshop.property.constants;

/**
 * 商家交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MerchantTransactionTypeEnum {
	
	/**
	 * 买单100
	 */
	PAY((byte)0x64),
	
	/**
	 * 订单101
	 */
	ORDER((byte)0x65),
	
	/**
	 * 下级收益102
	 */
	LOWER_INCOME((byte)0x66),
	
	/**
	 * 充值103
	 */
	RECHARGE((byte)0x67),
	
	/**
	 * 投放104
	 */
	PUT_ON((byte)0x68),
	
	/**
	 * 积分充值105
	 */
	INTEGRAL_RECHARGE((byte)0x69),
	
	/**
	 * 退款106
	 */
	REFUNDS((byte)0x70),
	
	/**
	 * 提现107
	 */
	WITHDRAW((byte)0x71),
	
	/**
	 * 提现108
	 */
	WITHDRAW_BACK((byte)0x72),
	
	/**
	 * 邀请粉丝109
	 */
	INVITE_FANS((byte)0x73),
	
	/**
	 * 发广告109
	 */
	ADD_AD((byte)0x74),
	
	/**
	 * 发红包110
	 */
	ADD_RED_PACKET((byte)0x75);
	
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