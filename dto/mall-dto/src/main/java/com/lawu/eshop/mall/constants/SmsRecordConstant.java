package com.lawu.eshop.mall.constants;

/**
 * 发送短信参数
 *
 * @author meishuquan
 * @date 2017/3/27
 */
public class SmsRecordConstant {

    /**
     * 短信模板
     */
    public static final String SMS_TEMPLATE = "您的验证码为{smsCode}请于30分钟内输入验证，若非本人操作，请忽略。";

    /**
     * 允许发送
     */
    public static final int SMS_SEND_CODE = 0;

    /**
     * 短信发送成功
     */
    public static final boolean SMS_SEND_SUCCESS = true;

    /**
     * 短信发送失败
     */
    public static final boolean SMS_SEND_FAIL = false;

    /**
     * 注册
     */
    public static final byte SMS_TYPE_REGISTER = 1;

    /**
     * 找回登录密码
     */
    public static final byte SMS_TYPE_FIND_LOGIN_PWD = 2;

    /**
     * 找回支付密码
     */
    public static final byte SMS_TYPE_FIND_PAY_PWD = 3;

}
