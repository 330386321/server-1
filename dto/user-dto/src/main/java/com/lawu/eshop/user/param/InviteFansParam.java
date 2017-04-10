package com.lawu.eshop.user.param;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
public class InviteFansParam {

    private Long merchantId;

    private String regionPath;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }
}
