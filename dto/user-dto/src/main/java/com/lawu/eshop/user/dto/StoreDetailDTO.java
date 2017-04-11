package com.lawu.eshop.user.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author meishuquan
 * @date 2017/4/7.
 */
public class StoreDetailDTO {

    @ApiModelProperty(value = "商家ID")
    private Long merchantId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "区域")
    private String regionPath;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "负责人手机号")
    private String principalMobile;

    @ApiModelProperty(value = "门店照")
    private String storePic;

    @ApiModelProperty(value = "店铺介绍")
    private String intro;

    @ApiModelProperty(value = "收藏人数")
    private Integer favoriteNumber;

    @ApiModelProperty(value = "人均消费")
    private BigDecimal averageConsumeAmount;

    @ApiModelProperty(value = "综合评分")
    private BigDecimal averageScore;

    @ApiModelProperty(value = "好评率")
    private BigDecimal feedbackRate;

    @ApiModelProperty(value = "优惠信息")
    private String preferentialClause;

    @ApiModelProperty(value = "优惠时间")
    private String preferentialTime;

    @ApiModelProperty(value = "有效时间")
    private String validTime;

    @ApiModelProperty(value = "已买笔数")
    private Integer buyNumbers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrincipalMobile() {
        return principalMobile;
    }

    public void setPrincipalMobile(String principalMobile) {
        this.principalMobile = principalMobile;
    }

    public String getStorePic() {
        return storePic;
    }

    public void setStorePic(String storePic) {
        this.storePic = storePic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPreferentialClause() {
        return preferentialClause;
    }

    public void setPreferentialClause(String preferentialClause) {
        this.preferentialClause = preferentialClause;
    }

    public String getPreferentialTime() {
        return preferentialTime;
    }

    public void setPreferentialTime(String preferentialTime) {
        this.preferentialTime = preferentialTime;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getFavoriteNumber() {
        return favoriteNumber;
    }

    public void setFavoriteNumber(Integer favoriteNumber) {
        this.favoriteNumber = favoriteNumber;
    }

    public void setAverageConsumeAmount(BigDecimal averageConsumeAmount) {
        this.averageConsumeAmount = averageConsumeAmount;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
    }

    public BigDecimal getFeedbackRate() {
        return feedbackRate;
    }

    public void setFeedbackRate(BigDecimal feedbackRate) {
        this.feedbackRate = feedbackRate;
    }

    public Integer getBuyNumbers() {
        return buyNumbers;
    }

    public void setBuyNumbers(Integer buyNumbers) {
        this.buyNumbers = buyNumbers;
    }

    public BigDecimal getAverageConsumeAmount() {
        return averageConsumeAmount;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }
}
