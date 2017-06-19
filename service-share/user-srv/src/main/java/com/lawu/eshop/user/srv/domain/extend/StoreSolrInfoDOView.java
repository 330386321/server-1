package com.lawu.eshop.user.srv.domain.extend;

import java.io.Serializable;

/**
 * @author zhangyong
 * @date 2017/6/15.
 */
public class StoreSolrInfoDOView implements Serializable{
    private static final long serialVersionUID = 7596982275194296481L;

    private Long merchantStoreId;

    private Long merchantId;

    private String industryPath;

    private String industryName;

    public Long getMerchantStoreId() {
        return merchantStoreId;
    }

    public void setMerchantStoreId(Long merchantStoreId) {
        this.merchantStoreId = merchantStoreId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getIndustryPath() {
        return industryPath;
    }

    public void setIndustryPath(String industryPath) {
        this.industryPath = industryPath;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}
