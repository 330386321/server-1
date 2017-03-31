package com.lawu.eshop.user.param;

import com.lawu.eshop.user.constants.MerchantAuditStatusEnum;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import io.swagger.annotations.ApiParam;

/**
 * @author zhangyong
 * @date 2017/3/31.
 */
public class MerchantAuditParam {

    /**
     * 门店审核状态
     */
    @ApiParam(name = "auditStatusEnum", value = "审核状态")
    private MerchantAuditStatusEnum auditStatusEnum;

    /**
     * 门店状态
     */
    @ApiParam(name = "storeStatusEnum", value = "门店状态")
    private MerchantStatusEnum storeStatusEnum;

    /**
     * 审核备注
     */
    @ApiParam(name = "remark", value = "审核备注")
    private String remark;

    /**
     * 门店id
     */
    @ApiParam(name = "merchantStoreId", value = "门店id")
    private Long merchantStoreId;

    private Integer auditorId;

    public MerchantAuditStatusEnum getAuditStatusEnum() {
        return auditStatusEnum;
    }

    public void setAuditStatusEnum(MerchantAuditStatusEnum auditStatusEnum) {
        this.auditStatusEnum = auditStatusEnum;
    }

    public MerchantStatusEnum getStoreStatusEnum() {
        return storeStatusEnum;
    }

    public void setStoreStatusEnum(MerchantStatusEnum storeStatusEnum) {
        this.storeStatusEnum = storeStatusEnum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getMerchantStoreId() {
        return merchantStoreId;
    }

    public void setMerchantStoreId(Long merchantStoreId) {
        this.merchantStoreId = merchantStoreId;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }
}
