package com.lawu.eshop.user.dto;

import com.lawu.eshop.user.constants.MerchantAuditStatusEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/14.
 */
public class MerchantAuditInfoDTO {

    @ApiModelProperty(value = "MERCHANT_AUDIT_STATUS_CHECKED:审核通过,MERCHANT_AUDIT_STATUS_CHECK_FAILED:审核不通过")
    private MerchantAuditStatusEnum merchantAuditStatusEnum;

    @ApiModelProperty(value = "审核备注")
    private String remark;

    public MerchantAuditStatusEnum getMerchantAuditStatusEnum() {
        return merchantAuditStatusEnum;
    }

    public void setMerchantAuditStatusEnum(MerchantAuditStatusEnum merchantAuditStatusEnum) {
        this.merchantAuditStatusEnum = merchantAuditStatusEnum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
