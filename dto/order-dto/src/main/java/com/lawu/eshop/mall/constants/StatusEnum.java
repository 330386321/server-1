package com.lawu.eshop.mall.constants;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
public enum StatusEnum {
    STATUS_DEL((byte) 0x00),//0：删除
    STATUS_UNPAY((byte) 0x01),//1-待支付
    STATUS_PAY_SUCCESS((byte) 0x02),//2-成功
    STATUS_PAY_fAIL((byte) 0x03);//3-失败
    public Byte val;

    StatusEnum(Byte val) {
        this.val = val;
    }

    public static StatusEnum getEnum(Byte val) {
        StatusEnum[] values = StatusEnum.values();
        for (StatusEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }
}
