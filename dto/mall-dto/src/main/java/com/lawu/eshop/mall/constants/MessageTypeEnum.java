package com.lawu.eshop.mall.constants;

/**
 * 消息类型枚举类
 * Created by Administrator on 2017/3/30.
 */
public enum MessageTypeEnum {
    MESSAGE_TYPE_RECOMMEND_STORE((byte) 0x01),//推荐店铺
    MESSAGE_TYPE_PLATFORM_NOTICE((byte) 0x02),//平台通知

    MESSAGE_TYPE_RECHARGE((byte) 0x03),//充值
    MESSAGE_TYPE_WITHDRAW((byte) 0x04),//提现
    MESSAGE_TYPE_INCOME((byte) 0x05),//收益

    MESSAGE_TYPE_START_SEND_NOTICE((byte) 0x06),//发货通知
    MESSAGE_TYPE_SENDING_NOTICE((byte) 0x07),//派送通知
    MESSAGE_TYPE_SIGN_NOTICE((byte) 0x08);//签收通知
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
