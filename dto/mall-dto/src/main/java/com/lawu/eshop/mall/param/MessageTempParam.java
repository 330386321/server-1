package com.lawu.eshop.mall.param;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author zhangyong
 * @date 2017/5/4.
 */
public class MessageTempParam {


    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "订单编号")
    private String orderNum;

    @ApiModelProperty(value = "运单编号")
    private String waybillNum;

    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "充值金额")
    private BigDecimal rechargeBalance;

    @ApiModelProperty(value = "当前积分")
    private BigDecimal point;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal expendAmount;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal favoredAmount;

    @ApiModelProperty(value = "退款编号")
    private String refundNum;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "收益金额")
    private BigDecimal earningAmount;

    @ApiModelProperty(value = "收益积分")
    private BigDecimal earningPoint;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "广告名称")
    private String adName;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "消费积分")
    private BigDecimal expendPoint;

    @ApiModelProperty(value = "充值编号")
    private String rechargeNum;

    @ApiModelProperty(value = "广告类型名称")
    private String adTypeName;

    @ApiModelProperty(value = "失败原因")
    private String failReason;

    @ApiModelProperty(value = "代发货商品数量")
    private Integer productCount;

    @ApiModelProperty(value = "快递名称")
    private String expressCompanyName;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal orderAmount;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getWaybillNum() {
        return waybillNum;
    }

    public void setWaybillNum(String waybillNum) {
        this.waybillNum = waybillNum;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getRechargeBalance() {
        return rechargeBalance;
    }

    public void setRechargeBalance(BigDecimal rechargeBalance) {
        this.rechargeBalance = rechargeBalance;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public BigDecimal getExpendAmount() {
        return expendAmount;
    }

    public void setExpendAmount(BigDecimal expendAmount) {
        this.expendAmount = expendAmount;
    }

    public BigDecimal getFavoredAmount() {
        return favoredAmount;
    }

    public void setFavoredAmount(BigDecimal favoredAmount) {
        this.favoredAmount = favoredAmount;
    }

    public String getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(String refundNum) {
        this.refundNum = refundNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getEarningAmount() {
        return earningAmount;
    }

    public void setEarningAmount(BigDecimal earningAmount) {
        this.earningAmount = earningAmount;
    }

    public BigDecimal getEarningPoint() {
        return earningPoint;
    }

    public void setEarningPoint(BigDecimal earningPoint) {
        this.earningPoint = earningPoint;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public BigDecimal getExpendPoint() {
        return expendPoint;
    }

    public void setExpendPoint(BigDecimal expendPoint) {
        this.expendPoint = expendPoint;
    }

    public String getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(String rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public String getAdTypeName() {
        return adTypeName;
    }

    public void setAdTypeName(String adTypeName) {
        this.adTypeName = adTypeName;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getExpressCompanyName() {
        return expressCompanyName;
    }

    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName;
    }
}
