package com.lawu.eshop.property.constants;

/**
 * 会员交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MemberTransactionTypeEnum {

	RECHARGE_BALANCE((byte) 0x01, "充值余额"),//
	ADVERTISING((byte) 0x02, "优选红包"),//--已废弃
	RED_SWEEP((byte) 0x03, "红包"),//--已废弃
	LOWER_INCOME((byte) 0x05, "推荐E友收益"),//
	PAY((byte) 0x06, "买单"),//
	PAY_ORDERS((byte) 0x07, "购物"),//
	RECHARGE_POINT((byte) 0x08, "充值积分"),//
	WITHDRAW((byte) 0x09, "提现"),//
	WITHDRAW_BACK((byte) 0x0A, "提现失败"),//
	REFUND_ORDERS((byte) 0x0B, "购物退款"),//
	SALES_COMMISSION((byte) 0x0C, "推荐E友收益"),//
	VOLUME_COMMISSION((byte) 0x0D, "推荐E友收益"),//
	BACKAGE((byte) 0x0F, "平台充值"),
	AD_QZ((byte) 0x10, "咻一咻"),//
	USER_REDPACKET_ADD((byte)0x11,"红包未领取"),//
	ADD_RED_SWEEP((byte)0x12,"发红包"),//
	MEMBER_FANS((byte) 0x13, "成为粉丝"),

	MERCHANT_RED_SWEEP((byte) 0x14, "商家红包"),//
	MEMBER_RED_SWEEP((byte) 0x15, "个人红包"),//
	PLATFORM_RED_SWEEP((byte) 0x16, "平台红包"),
	AD_PLANE((byte) 0x17, "猜一猜"),//
	AD_VIDEO((byte) 0x18, "看一看");//


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