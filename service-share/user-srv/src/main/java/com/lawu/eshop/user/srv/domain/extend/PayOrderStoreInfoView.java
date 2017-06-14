package com.lawu.eshop.user.srv.domain.extend;

import java.io.Serializable;

/**
 * @author zhangyong
 * @date 2017/6/14.
 */
public class PayOrderStoreInfoView implements Serializable {
    private static final long serialVersionUID = 2542725345089473174L;

    private String name;

    private String path;

    private Long merchantId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
}
