package com.lawu.eshop.user.dto;

import io.swagger.annotations.ApiModelProperty;

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
    private Integer favCount;

    @ApiModelProperty(value = "人均消费")
    private Double avgConsume;

    @ApiModelProperty(value = "综合评分")
    private Double score;

    @ApiModelProperty(value = "好评率")
    private Double goodCommentRate;

    @ApiModelProperty(value = "优惠信息")
    private String preferentialClause;

    @ApiModelProperty(value = "优惠时间")
    private String preferentialTime;

    @ApiModelProperty(value = "买单笔数")
    private Integer consumeCount;

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

    public Integer getFavCount() {
        return favCount;
    }

    public void setFavCount(Integer favCount) {
        this.favCount = favCount;
    }

    public Double getAvgConsume() {
        return avgConsume;
    }

    public void setAvgConsume(Double avgConsume) {
        this.avgConsume = avgConsume;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getGoodCommentRate() {
        return goodCommentRate;
    }

    public void setGoodCommentRate(Double goodCommentRate) {
        this.goodCommentRate = goodCommentRate;
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

    public Integer getConsumeCount() {
        return consumeCount;
    }

    public void setConsumeCount(Integer consumeCount) {
        this.consumeCount = consumeCount;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
}
