package com.lawu.eshop.product.dto;

import java.math.BigDecimal;

/**
 * 抢购活动商品分页列表DTO
 * 
 * @author jiangxinjun
 * @createDate 2017年11月24日
 * @updateDate 2017年11月24日
 */
public class SeckillActivityProductBuyPageDTO {
    
    /**
     * 抢购活动id
     */
    private Long activityId;
    
    /**
     * 抢购活动商品id
     */
    private Long activityProductId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品图片
     */
    private String productPicture;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品原价
     */
    private BigDecimal originalPrice;

    /**
     * 商品型号总数量
     */
    private Integer productModelCount;

    /**
     * 剩余数量
     */
    private Integer leftCount;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActivityProductId() {
        return activityProductId;
    }

    public void setActivityProductId(Long activityProductId) {
        this.activityProductId = activityProductId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getProductModelCount() {
        return productModelCount;
    }

    public void setProductModelCount(Integer productModelCount) {
        this.productModelCount = productModelCount;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }
}