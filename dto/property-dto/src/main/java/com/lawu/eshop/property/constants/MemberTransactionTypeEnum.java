package com.lawu.eshop.property.constants;

/**
 * 会员交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MemberTransactionTypeEnum {

	RECHARGE((byte) 0x01, "余额充值"),
	ADVERTISING((byte) 0x02, "点广告"),
	RED_SWEEP((byte) 0x03, "扫红包"),
	REFUNDS((byte) 0x04, "退款"),
	LOWER_INCOME((byte) 0x05, " 下级点击广告提成"),
	PAY((byte) 0x06, "买单"),
	PAY_ORDERS((byte) 0x07, "付商品订单"),
	INTEGRAL_RECHARGE((byte) 0x08, "积分充值"),
	WITHDRAW((byte) 0x09, "提现"),
	WITHDRAW_BACK((byte) 0x0A, "提现退回"),
	REFUND_ORDERS((byte) 0x0B, "退商品订单");

	private Byte value;

	private String name;

	MemberTransactionTypeEnum(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}