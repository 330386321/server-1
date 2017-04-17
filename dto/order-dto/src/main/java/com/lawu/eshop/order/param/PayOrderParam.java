package com.lawu.eshop.order.param;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import com.lawu.eshop.order.constants.TransactionPayTypeEnum;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
public class PayOrderParam {

    @ApiModelProperty(value = "商家ID")
    private Long merchantId;

    /**
     * 原价
     */
    @ApiModelProperty(value = "原价")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "实际金额")
    private BigDecimal actualAmount;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal favoredAmount;

    @ApiModelProperty(value = "BALANCE:余额，ALIPAY：支付宝,WX:微信",required = true)
    private TransactionPayTypeEnum payTypeEnum;
    @ApiModelProperty(value = "商家编号")
    private String merchantNum;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getFavoredAmount() {
        return favoredAmount;
    }

    public void setFavoredAmount(BigDecimal favoredAmount) {
        this.favoredAmount = favoredAmount;
    }

    public TransactionPayTypeEnum getPayTypeEnum() {
        return payTypeEnum;
    }

    public void setPayTypeEnum(TransactionPayTypeEnum payTypeEnum) {
        this.payTypeEnum = payTypeEnum;
    }

    public String getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(String merchantNum) {
        this.merchantNum = merchantNum;
    }
}
