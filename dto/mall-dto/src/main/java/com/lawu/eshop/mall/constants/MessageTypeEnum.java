package com.lawu.eshop.mall.constants;

/**
 * 消息类型枚举类
 * Created by Administrator on 2017/3/30.
 */
public enum MessageTypeEnum {
    MESSAGE_TYPE_REFUND((byte) 0x01),//退款
    MESSAGE_TYPE_APPRAISE((byte) 0x02),//评价
    MESSAGE_TYPEUS_NOTICE((byte) 0x03);//通知
    public Byte val;

    MessageTypeEnum(Byte val) {
        this.val = val;
    }

    public static MessageTypeEnum getEnum(Byte val) {
        MessageTypeEnum[] values = MessageTypeEnum.values();
        for (MessageTypeEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }
}
