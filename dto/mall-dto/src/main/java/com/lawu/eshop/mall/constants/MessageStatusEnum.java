package com.lawu.eshop.mall.constants;

/**
 * 信息状态枚举类
 * Created by zhangyong on 2017/3/29.
 */
public enum MessageStatusEnum {

    MESSAGE_STATUS_UNREAD((byte) 0x00),
    message_status_read((byte) 0x01);
    public Byte val;
    MessageStatusEnum(Byte val) {
        this.val = val;
    }

}
