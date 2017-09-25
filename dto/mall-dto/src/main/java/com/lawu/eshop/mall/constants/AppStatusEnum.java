package com.lawu.eshop.mall.constants;

/**
 * @author zhangrc
 * @date 2017/9/25.
 */
public enum AppStatusEnum {
	DELETE((byte) 0x00),
    ENABLE((byte) 0x01),//启用
    DISENABLE((byte) 0x02);//禁用
    public Byte val;

    AppStatusEnum(Byte val) {
        this.val = val;
    }

    public static AppStatusEnum getEnum(Byte val) {
        AppStatusEnum[] values = AppStatusEnum.values();
        for (AppStatusEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }
}
