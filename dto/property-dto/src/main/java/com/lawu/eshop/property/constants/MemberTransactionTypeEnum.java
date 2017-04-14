package com.lawu.eshop.property.constants;

/**
 * 会员交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MemberTransactionTypeEnum {
	
	/**
	 * 余额充值
	 */
	RECHARGE((byte)0x01),
	
	/**
	 * 广告
	 */
	ADVERTISING((byte)0x02),
	
	/**
	 * 扫红包
	 */
	RED_SWEEP((byte)0x03),
	
	/**
	 * 退款
	 */
	REFUNDS((byte)0x04),
	
	/**
	 * 下级收益
	 */
	LOWER_INCOME((byte)0x05),
	
	/**
	 * 买单
	 */
	PAY((byte)0x06),
	
	/**
	 * 付商品订单
	 */
	PAY_ORDERS((byte)0x07),
	
	/**
	 * 积分充值
	 */
	INTEGRAL_RECHARGE((byte)0x08),
	
	/**
	 * 提现
	 */
	WITHDRAW((byte)0x09),
	
	/**
	 * 提现退回11
	 */
	WITHDRAW_BACK((byte)0x0A),
	
	/**
	 * 退商品订单12
	 */
	REFUND_ORDERS((byte)0x0B);
	
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