package com.lawu.eshop.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 用户端商品详情封装DTO
 *
 * @author Leach
 * @date 2017/3/22
 */
public class ProductInfoDTO {

    @ApiModelProperty(value = "商品ID")
    private Long id;
    @ApiModelProperty(value = "商家ID")
    private Long merchantId;
    @ApiModelProperty(value = "商品名称")
    private String name;
    @ApiModelProperty(value = "商品描述")
    private String content;
    @ApiModelProperty(value = "销量")
    private Integer totalSales;
    @ApiModelProperty(value = "封面图片")
    private String featureImage;
    @ApiModelProperty(value = "滚动图片")
    private String imagesHeadUrl;
    @ApiModelProperty(value = "详情图片")
    private String imageDetailUrl;
    @ApiModelProperty(value = "型号信息")
    private String spec;
    @ApiModelProperty(value = "型号最大价格")
    private String priceMax;
    @ApiModelProperty(value = "型号最小价格")
    private String priceMin;
    @ApiModelProperty(value = "是否支持七天无理由退货")
    private boolean isSupportEleven;
    @ApiModelProperty(value = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    public boolean isSupportEleven() {
        return isSupportEleven;
    }

    public void setSupportEleven(boolean isSupportEleven) {
        this.isSupportEleven = isSupportEleven;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeatureImage() {
        return featureImage;
    }

    public void setFeatureImage(String featureImage) {
        this.featureImage = featureImage;
    }

    public String getImagesHeadUrl() {
        return imagesHeadUrl;
    }

    public void setImagesHeadUrl(String imagesHeadUrl) {
        this.imagesHeadUrl = imagesHeadUrl;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getImageDetailUrl() {
        return imageDetailUrl;
    }

    public void setImageDetailUrl(String imageDetailUrl) {
        this.imageDetailUrl = imageDetailUrl;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
