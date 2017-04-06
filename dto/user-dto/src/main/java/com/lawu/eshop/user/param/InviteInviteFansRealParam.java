package com.lawu.eshop.user.param;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
public class InviteInviteFansRealParam extends InviteFansParam {

    private Long merchantId;

    private Byte sex;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }
}
