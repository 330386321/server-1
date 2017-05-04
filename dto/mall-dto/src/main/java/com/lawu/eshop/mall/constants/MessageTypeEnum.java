package com.lawu.eshop.mall.constants;

/**
 * 消息类型枚举类
 * Created by Administrator on 2017/3/30.
 */
public enum MessageTypeEnum {
    MESSAGE_TYPE_INVITE_FANS((byte) 0x01),//邀请粉丝
    MESSAGE_TYPE_ORDER_PAY((byte) 0x02),//订单付款

    MESSAGE_TYPE_ACTIVE((byte) 0x03),//活动消息
    MESSAGE_TYPE_ORDER_START_SEND((byte) 0x04),//订单已发货
    MESSAGE_TYPE_ORDER_SENDING((byte) 0x05),//订单正在派件
    MESSAGE_TYPE_ORDER_SIGN((byte) 0x05),//订单已签收

    MESSAGE_TYPE_WITHDRAW_APPLY((byte) 0x06),//提现申请
    MESSAGE_TYPE_WITHDRAW_SUCCESS((byte) 0x07),//提现成功
    MESSAGE_TYPE_WITHDRAW_FAIL((byte) 0x08),//提现失败

    MESSAGE_TYPE_RECHARGE_BALANCE((byte) 0x09),//余额充值
    MESSAGE_TYPE_RECHARGE_POINT((byte) 0x10),//积分充值

    MESSAGE_TYPE_PAY_ORDER_SUCCESS((byte) 0x11),//买单成功
    MESSAGE_TYPE_REFUND_AGREE((byte) 0x12),//同意退款
    MESSAGE_TYPE_REFUND_REFUSE((byte) 0x13),//拒绝退款
    MESSAGE_TYPE_REFUND_SUCCESS((byte) 0x14),//退款成功
    MESSAGE_TYPE_PAYMENT_SUCCESS((byte) 0x15),//付款成功
    MESSAGE_TYPE_RECOMMEND_MEMBER_BALANCE((byte) 0x16),//推荐E友获取现金
    MESSAGE_TYPE_RECOMMEND_MEMBER_POINT((byte) 0x17),//推荐E友获取积分
    MESSAGE_TYPE_RECOMMEND_MERCHANT_BALANCE((byte) 0x18),//推荐商家获取现金
    MESSAGE_TYPE_RECOMMEND_MERCHANT_POINT((byte) 0x19),//推荐商家获取积分


    MESSAGE_TYPE_CHECK_AD_SUCCESS((byte) 0x20),//ad审核通过
    MESSAGE_TYPE_CHECK_AD_FAIL((byte) 0x21),//ad审核不通过
    MESSAGE_TYPE_CHECK_STORE_SUCCESS((byte) 0x22),//门店审核通过
    MESSAGE_TYPE_CHECK_STORE_FAIL((byte) 0x23),//门店审核不通过
    MESSAGE_TYPE_CHECK_DEPOSIT_SUCCESS((byte) 0x24),//保证金审核通过

    //商家端
    MESSAGE_TYPE_NEW_ORDER((byte) 0x25),//新增订单
    MESSAGE_TYPE_SEND_NOTICE((byte) 0x26),//商家发货提醒
    MESSAGE_TYPE_RETURN_NOTICE((byte) 0x27),//商家退货提醒
    MESSAGE_TYPE_USER_SEND((byte) 0x28),//用户已发货

    MESSAGE_TYPE_PLATFORM_NOTICE((byte) 0x29),//平台通知

    MESSAGE_TYPE_REDPACKET((byte) 0x30);//现金红包

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
