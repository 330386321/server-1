package com.lawu.eshop.property.param;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class AgentRechargeReportParam {

    @NotNull(message = "gmtReport不能为空")
    private Date gmtReport;

    @NotNull(message = "gmtCreate不能为空")
    private Date gmtCreate;

    @NotNull(message = "merchantRechargeBalance不能为空")
    private BigDecimal merchantRechargeBalance;

    @NotNull(message = "merchantRechargePoint不能为空")
    private BigDecimal merchantRechargePoint;

    @NotNull(message = "memberRechargeBalance不能为空")
    private BigDecimal memberRechargeBalance;

    @NotNull(message = "memberRechargePoint不能为空")
    private BigDecimal memberRechargePoint;

    @NotNull(message = "totalRechargeBalance不能为空")
    private BigDecimal totalRechargeBalance;

    @NotNull(message = "totalRechargePoint不能为空")
    private BigDecimal totalRechargePoint;

    public Date getGmtReport() {
        return gmtReport;
    }

    public void setGmtReport(Date gmtReport) {
        this.gmtReport = gmtReport;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public BigDecimal getMerchantRechargeBalance() {
        return merchantRechargeBalance;
    }

    public void setMerchantRechargeBalance(BigDecimal merchantRechargeBalance) {
        this.merchantRechargeBalance = merchantRechargeBalance;
    }

    public BigDecimal getMerchantRechargePoint() {
        return merchantRechargePoint;
    }

    public void setMerchantRechargePoint(BigDecimal merchantRechargePoint) {
        this.merchantRechargePoint = merchantRechargePoint;
    }

    public BigDecimal getMemberRechargeBalance() {
        return memberRechargeBalance;
    }

    public void setMemberRechargeBalance(BigDecimal memberRechargeBalance) {
        this.memberRechargeBalance = memberRechargeBalance;
    }

    public BigDecimal getMemberRechargePoint() {
        return memberRechargePoint;
    }

    public void setMemberRechargePoint(BigDecimal memberRechargePoint) {
        this.memberRechargePoint = memberRechargePoint;
    }

    public BigDecimal getTotalRechargeBalance() {
        return totalRechargeBalance;
    }

    public void setTotalRechargeBalance(BigDecimal totalRechargeBalance) {
        this.totalRechargeBalance = totalRechargeBalance;
    }

    public BigDecimal getTotalRechargePoint() {
        return totalRechargePoint;
    }

    public void setTotalRechargePoint(BigDecimal totalRechargePoint) {
        this.totalRechargePoint = totalRechargePoint;
    }
}