package com.lawu.eshop.property.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MemberTransactionTypeEnum {

	RECHARGE_BALANCE((byte) 0x01, "余额充值", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RECHARGE},"余额充值"),
	//ADVERTISING((byte) 0x02, "优选红包", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.PREFERRED_RED_SWEEP},""),
	//RED_SWEEP((byte) 0x03, "红包", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RED_SWEEP},""),
	LOWER_INCOME((byte) 0x05, "推荐E友收益", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RECOMMEND_INCOME},"推荐E友收益"),
	PAY((byte) 0x06, "买单", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.PAY},""),
	PAY_ORDERS((byte) 0x07, "购物", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.SHOPPING},""),
	RECHARGE_POINT((byte) 0x08, "积分充值", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RECHARGE},"积分充值"),
	WITHDRAW((byte) 0x09, "提现", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.WITHDRAW},"提现申请"),
	WITHDRAW_BACK((byte) 0x0A, "提现失败", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.WITHDRAW, MemberTransactionCategoryEnum.REFUND_MONEY},"提现失败-退款"),
	REFUND_ORDERS((byte) 0x0B, "购物退款", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.REFUND_MONEY},""),
	SALES_COMMISSION((byte) 0x0C, "推荐E友收益", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RECOMMEND_INCOME}, "推荐E友收益"),
	VOLUME_COMMISSION((byte) 0x0D, "推荐E友收益", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RECOMMEND_INCOME}, "推荐E友收益"),
	BACKAGE((byte) 0x0F, "平台充值", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RECHARGE},""),
	AD_QZ((byte) 0x10, "咻一咻", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.PREFERRED_RED_SWEEP},"咻一咻-"),
	USER_REDPACKET_ADD((byte)0x11, "红包未领取", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.REFUND_MONEY},"红包未领取"),
	ADD_RED_SWEEP((byte)0x12,"发红包", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RED_SWEEP},"发红包"),
	//MEMBER_FANS((byte) 0x13, "成为粉丝", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RED_SWEEP},"商家红包"),
	MERCHANT_RED_SWEEP((byte) 0x14, "商家红包", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RED_SWEEP},"商家红包-"),
	MEMBER_RED_SWEEP((byte) 0x15, "个人红包", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RED_SWEEP},"个人红包-"),
	PLATFORM_RED_SWEEP((byte) 0x16, "E店红包", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.RED_SWEEP},"E店红包"),
	AD_PLANE((byte) 0x17, "猜一猜", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.PREFERRED_RED_SWEEP},"猜一猜-"),
	AD_VIDEO((byte) 0x18, "看一看", new MemberTransactionCategoryEnum[]{MemberTransactionCategoryEnum.PREFERRED_RED_SWEEP},"看一看-"),
    POINT_CONVERT_LOTTERY((byte) 0x19, "积分兑换抽奖", null,"抽奖");//

	private Byte value;

	private String name;
	
	private MemberTransactionCategoryEnum[] category;

	private String descPrefix;

	MemberTransactionTypeEnum(Byte value, String name, MemberTransactionCategoryEnum[] category,String descPrefix) {
		this.value = value;
		this.name = name;
		this.category = category;
		this.descPrefix = descPrefix;
	}

	public Byte getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public MemberTransactionCategoryEnum[] getCategory() {
        return category;
    }

	public String getDescPrefix() {
		return descPrefix;
	}

	public MemberTransactionCategoryEnum getPriorityCategory() {
        return category[0];
    }

	/**
	 * 根据交易种类获取相应的交易类型
	 * @param category 交易种类
	 * @return
	 * @author jiangxinjun
	 * @date 2017年10月20日
	 */
    public static List<Byte> getEnum(MemberTransactionCategoryEnum category) {
        List<Byte> rtn = null;
		MemberTransactionTypeEnum[] values = MemberTransactionTypeEnum.values();
		for (MemberTransactionTypeEnum object : values) {
			if (object.getCategory() != null) {
			    for (MemberTransactionCategoryEnum categoryItem : object.getCategory()) {
			        if (categoryItem.equals(category)) {
			            if (rtn == null) {
			                rtn = new ArrayList<>();
			            }
			            rtn.add(object.getValue());
			        }
			    }
			}
		}
		return rtn;
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