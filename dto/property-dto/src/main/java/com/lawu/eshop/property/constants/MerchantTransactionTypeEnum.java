package com.lawu.eshop.property.constants;

/**
 * 商家交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MerchantTransactionTypeEnum {
	
	/**
	 * 买单
	 */
	PAY((byte)0x101),
	
	/**
	 * 订单
	 */
	ORDER((byte)0x102),
	
	/**
	 * 下级收益
	 */
	LOWER_INCOME((byte)0x103),
	
	/**
	 * 充值
	 */
	RECHARGE((byte)0x104),
	
	/**
	 * 投放
	 */
	PUT_ON((byte)0x105),
	
	/**
	 * 积分充值
	 */
	INTEGRAL_RECHARGE((byte)0x106),
	
	/**
	 * 退款
	 */
	REFUNDS((byte)0x107),
	
	/**
	 * 提现
	 */
	WITHDRAW((byte)0x108);
	
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