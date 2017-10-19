package com.lawu.eshop.ad.dto;

import java.math.BigDecimal;


public class AdPayInfoDTO {


    private BigDecimal totalPoint;

    private String merchantRegionPath;

    public BigDecimal getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(BigDecimal totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getMerchantRegionPath() {
        return merchantRegionPath;
    }

    public void setMerchantRegionPath(String merchantRegionPath) {
        this.merchantRegionPath = merchantRegionPath;
    }
}
