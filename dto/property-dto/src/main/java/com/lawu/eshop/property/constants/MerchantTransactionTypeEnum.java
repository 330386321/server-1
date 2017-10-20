package com.lawu.eshop.property.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 商家交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MerchantTransactionTypeEnum {
	
	PAY((byte)0x64, "买单收入", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.PAY_INCOME}),						//100--
	ORDER((byte)0x65, "商品收入", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.PRODUCT_INCOME}),						//101--
	LOWER_INCOME((byte)0x66, "广告提成", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECOMMEND_INCOME}),				//102--
	RECHARGE((byte)0x67, "充值余额", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECHARGE}),					//103--
	INTEGRAL_RECHARGE((byte)0x69, "充值积分", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECHARGE}),				//105--
	WITHDRAW((byte)0x6B,"提现成功", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.WITHDRAW}),					//107--
	WITHDRAW_BACK((byte)0x6C,"提现失败", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.WITHDRAW, MerchantTransactionCategoryEnum.REFUND_MONEY}),				//108--
	INVITE_FANS((byte)0x6D,"邀请粉丝", null),					//109-积分明细类型
	ADD_AD((byte)0x6E, "投放广告", null),						//110-积分明细类型
	ADD_RED_PACKET((byte)0x6F, "发红包", null),				//111-积分明细类型
	AD_RETURN_POINT((byte)0x70, "积分退还", null),				//112-积分明细类型
//	DEPOSIT((byte)0x71,"缴纳保证金"),					//113-需求说不要
//	DEPOSIT_REFUND((byte)0x72,"保证金退款"),				//114-需求说不要
	SALES_COMMISSION((byte) 0x73, "推荐E友收益", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECOMMEND_INCOME}),			//115--
	VOLUME_COMMISSION((byte) 0x74, "推荐商家收益", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECOMMEND_INCOME}),			//116--
	BACKAGE((byte) 0x75, "平台充值", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECHARGE}),					//117
      AD_DOWN((byte) 0x76, "红包未领取", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.REFUND_MONEY}),					//118
	RED_PACKET((byte) 0x77, "红包", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RED_PACKET});

	private Byte value;

	private String name;

	private MerchantTransactionCategoryEnum[] category;

	MerchantTransactionTypeEnum(Byte value, String name, MerchantTransactionCategoryEnum[] category) {
		this.value = value;
		this.name = name;
		this.category = category;
	}

	public Byte getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public MerchantTransactionCategoryEnum[] getCategory() {
        return category;
    }
    public MerchantTransactionCategoryEnum getPriorityCategory() {
        return category[0];
    }

	/**
     * 根据交易种类获取相应的交易类型
     * @param category 交易种类
     * @return
     * @author jiangxinjun
     * @date 2017年10月20日
     */
    public static List<Byte> getEnum(MerchantTransactionCategoryEnum category) {
        List<Byte> rtn = null;
        MerchantTransactionTypeEnum[] values = MerchantTransactionTypeEnum.values();
        for (MerchantTransactionTypeEnum object : values) {
            if (object.getCategory() != null) {
                for (MerchantTransactionCategoryEnum categoryItem : object.getCategory()) {
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

    public static MerchantTransactionTypeEnum getEnum(Byte val) {
		MerchantTransactionTypeEnum[] values = MerchantTransactionTypeEnum.values();
		for (MerchantTransactionTypeEnum object : values) {
			if (object.getValue().equals(val)) {
				return object;
			}
		}
		return null;
	}
}