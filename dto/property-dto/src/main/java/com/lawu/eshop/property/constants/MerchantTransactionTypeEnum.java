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

    PAY((byte) 0x64, "买单", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.PAY_INCOME}, ""),
    ORDER((byte) 0x65, "商品收入", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.PRODUCT_INCOME}, ""),
    LOWER_INCOME((byte) 0x66, "推荐E友收益", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECOMMEND_INCOME}, "推荐E友收益"),
    RECHARGE((byte) 0x67, "余额充值", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECHARGE}, "余额充值"),
    INTEGRAL_RECHARGE((byte) 0x69, "积分充值", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECHARGE}, "积分充值"),
    WITHDRAW((byte) 0x6B, "提现", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.WITHDRAW}, "提现申请"),
    WITHDRAW_BACK((byte) 0x6C, "提现失败", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.WITHDRAW, MerchantTransactionCategoryEnum.REFUND_MONEY}, "提现失败"),
    INVITE_FANS((byte) 0x6D, "邀请粉丝", null, ""),
    ADD_AD((byte) 0x6E, "投放广告", null, ""),
    ADD_RED_PACKET((byte) 0x6F, "发红包", null, ""),
    AD_RETURN_POINT((byte) 0x70, "积分退还", null, ""),
    //	DEPOSIT((byte)0x71,"缴纳保证金"),
//	DEPOSIT_REFUND((byte)0x72,"保证金退款"),
    SALES_COMMISSION((byte) 0x73, "推荐E友收益", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECOMMEND_INCOME}, "推荐E友收益"),
    VOLUME_COMMISSION((byte) 0x74, "推荐E友收益", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECOMMEND_INCOME}, "推荐E友收益"),
    BACKAGE((byte) 0x75, "平台充值", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RECHARGE}, ""),
    AD_DOWN((byte) 0x76, "红包未领取", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.REFUND_MONEY}, "红包未领取"),
    RED_PACKET((byte) 0x77, "红包", new MerchantTransactionCategoryEnum[]{MerchantTransactionCategoryEnum.RED_PACKET}, "发红包");

    private Byte value;

    private String name;

    private MerchantTransactionCategoryEnum[] category;

    private String descPrefix;

    MerchantTransactionTypeEnum(Byte value, String name, MerchantTransactionCategoryEnum[] category, String descPrefix) {
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

    public String getDescPrefix() {
        return descPrefix;
    }

    public MerchantTransactionCategoryEnum[] getCategory() {
        return category;
    }

    public MerchantTransactionCategoryEnum getPriorityCategory() {
        return category[0];
    }

    /**
     * 根据交易种类获取相应的交易类型
     *
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