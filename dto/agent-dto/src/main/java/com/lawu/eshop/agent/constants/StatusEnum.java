package com.lawu.eshop.agent.constants;

/**
 * @author zhangyong
 * @date 2017/8/9.
 */
public enum StatusEnum {
    STATUS_VALID((byte) 0x01),//有效
    STATUS_INVALID((byte) 0x00);//无效
    private Byte val;
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

    public Byte getVal() {
        return val;
    }
}
