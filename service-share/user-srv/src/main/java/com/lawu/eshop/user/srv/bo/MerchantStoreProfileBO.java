package com.lawu.eshop.user.srv.bo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商家门店信息
 * Created by zhangyong on 2017/3/24.
 */
public class MerchantStoreProfileBO {


    /**
     * 商家id
     */
    private Long merchantId;

    /**
     * 负责人名字
     */
    private String principalName;

    /**
     * 负责人手机
     */
    private String principalMobile;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalMobile() {
        return principalMobile;
    }

    public void setPrincipalMobile(String principalMobile) {
        this.principalMobile = principalMobile;
    }
}
