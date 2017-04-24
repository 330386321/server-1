package com.lawu.eshop.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawu.eshop.user.constants.MerchantAuditStatusEnum;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author zhangyong
 * @date 2017/4/14.
 */
public class MerchantAuditInfoDTO {

    @ApiModelProperty(value = "审核状态：MERCHANT_AUDIT_STATUS_CHECKED:审核通过,MERCHANT_AUDIT_STATUS_CHECK_FAILED:审核不通过，MERCHANT_AUDIT_STATUS_UNCHECK:未审核")
    private MerchantAuditStatusEnum merchantAuditStatusEnum;

    @ApiModelProperty(value = "审核备注")
    private String remark;
    @ApiModelProperty(value = "门店状态：MERCHANT_STATUS_UNCHECK:未审核," +
            "MERCHANT_STATUS_CHECKED:审核通过,MERCHANT_STATUS_CHECK_FAILED:审核不通过,MERCHANT_STATUS_NOT_MONEY:未提交保证金" +
            "MERCHANT_STATUS_GIVE_MONEY_CHECK:已提交保证金待财务核实,MERCHANT_STATUS_GIVE_MONEY_CHECK_FAILED:财务审核不通过,MERCHANT_STATUS_CANCEL:注销")
    private MerchantStatusEnum storeStatus;

    @ApiModelProperty(value = "提交审核时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;

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

    public MerchantStatusEnum getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(MerchantStatusEnum storeStatus) {
        this.storeStatus = storeStatus;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
