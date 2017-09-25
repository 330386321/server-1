package com.lawu.eshop.mall.constants;

/**
 * @author zhangrc
 * @date 2017/9/25.
 */
public enum AppTypeEnum {
    MEMBER((byte) 0x01),//会员
    MERCHANT((byte) 0x02);//商家
    public Byte val;

    AppTypeEnum(Byte val) {
        this.val = val;
    }

    public static AppTypeEnum getEnum(Byte val) {
        AppTypeEnum[] values = AppTypeEnum.values();
        for (AppTypeEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }
}
