package com.lawu.eshop.user.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
public class NearStoreDTO {

    @ApiModelProperty(value = "商户ID")
    private Long merchantId;

    @ApiModelProperty(value = "门店名称")
    private String name;

    @ApiModelProperty(value = "主营业务")
    private String industryPath;

    @ApiModelProperty(value = "门店logo")
    private String logo;

    @ApiModelProperty(value = "距离")
    private Integer distance;

    @ApiModelProperty(value = "收藏数")
    private Integer favCount;

    @ApiModelProperty(value = "平均评分")
    private Double averageScore;

    @ApiModelProperty(value = "人均消费")
    private Double averageConsume;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustryPath() {
        return industryPath;
    }

    public void setIndustryPath(String industryPath) {
        this.industryPath = industryPath;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getFavCount() {
        return favCount;
    }

    public void setFavCount(Integer favCount) {
        this.favCount = favCount;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Double getAverageConsume() {
        return averageConsume;
    }

    public void setAverageConsume(Double averageConsume) {
        this.averageConsume = averageConsume;
    }
}
