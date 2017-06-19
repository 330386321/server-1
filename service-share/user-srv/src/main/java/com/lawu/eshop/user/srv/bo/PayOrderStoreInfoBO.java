package com.lawu.eshop.user.srv.bo;

/**
 * @author zhangyong
 * @date 2017/6/14.
 */
public class PayOrderStoreInfoBO {

    private Long merchantId;

    private String name;

    private String storeUrl;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }
}
