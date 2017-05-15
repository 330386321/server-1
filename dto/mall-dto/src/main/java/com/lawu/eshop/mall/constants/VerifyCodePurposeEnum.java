package com.lawu.eshop.mall.constants;

/**
 * @author meishuquan
 * @date 2017/3/28.
 */
public enum VerifyCodePurposeEnum {

    PIC_VERIFY_CODE((byte) 0x00),    //图片验证码
    USER_REGISTER((byte) 0x01),     //注册
    FIND_LOGIN_PWD((byte) 0x02),    //找回登录密码
    FIND_PAY_PWD((byte) 0x03),    //找回登录密码
    REFUND_BOND((byte) 0x04);      //退保证金
    public Byte val;

    VerifyCodePurposeEnum(Byte val) {
        this.val = val;
    }

    public static VerifyCodePurposeEnum getEnum(Byte val) {
        VerifyCodePurposeEnum[] values = VerifyCodePurposeEnum.values();
        for (VerifyCodePurposeEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }
}
