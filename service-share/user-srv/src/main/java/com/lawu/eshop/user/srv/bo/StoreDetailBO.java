package com.lawu.eshop.user.srv.bo;

import java.math.BigDecimal;

/**
 * @author meishuquan
 * @date 2017/4/7.
 */
public class StoreDetailBO {

    private Long merchantId;

    private String name;

    private String address;

    private String principalMobile;

    private String storePic;

    private Integer picCount;

    private String intro;

    private Integer favoriteNumber;

    private BigDecimal averageConsumeAmount;

    private BigDecimal averageScore;

    private BigDecimal feedbackRate;

    private Integer buyNumbers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public BigDecimal getAverageConsumeAmount() {
        return averageConsumeAmount;
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

    public Integer getPicCount() {
        return picCount;
    }

    public void setPicCount(Integer picCount) {
        this.picCount = picCount;
    }
}
