package com.lawu.eshop.property.constants;

/**
 * 会员交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MemberTransactionTypeEnum {

	RECHARGE((byte) 0x01, "余额充值"),
	ADVERTISING((byte) 0x02, "看广告"),
	RED_SWEEP((byte) 0x03, "红包"),
	LOWER_INCOME((byte) 0x05, " 广告提成"),
	PAY((byte) 0x06, "买单"),
	PAY_ORDERS((byte) 0x07, "购物"),
	INTEGRAL_RECHARGE((byte) 0x08, "积分充值"),
	WITHDRAW((byte) 0x09, "提现"),
	WITHDRAW_BACK((byte) 0x0A, "提现失败"),
	REFUND_ORDERS((byte) 0x0B, "退款"),
	SALES_COMMISSION((byte) 0x0C, "推荐E友收益"),
	VOLUME_COMMISSION((byte) 0x0D, "推荐商家收益"),
	PRAISE_AD((byte) 0x0E, "抢赞消费"),//抢赞大于300积分的广告扣除20积分
	BACKAGE((byte) 0x0F, "平台充值"),//后台充值
	AD_QZ((byte) 0x10, "抢赞"),
	USER_REDPACKET_ADD((byte)0x11,"用户红包退款"),
	USER_REDPACKET_CUT((byte)0x12,"扣除用户发红包金额"),
	MEMBER_FANS((byte) 0x13, "成为粉丝");
	

	private Byte value;

	private String name;

	MemberTransactionTypeEnum(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public Byte getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static MemberTransactionTypeEnum getEnum(Byte val) {
		MemberTransactionTypeEnum[] values = MemberTransactionTypeEnum.values();
		for (MemberTransactionTypeEnum object : values) {
			if (object.getValue().equals(val)) {
				return object;
			}
		}
		return null;
	}
}