package com.lawu.eshop.user.param;

import java.util.Date;

/**
 * @author meishuquan
 * @date 2017/9/22.
 */
public class UserFansParam {

    private Long merchantId;

    private Date gmtCreate;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
