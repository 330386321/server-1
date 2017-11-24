package com.lawu.eshop.product.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 当天抢购活动商品列表DTO
 * 
 * @author jiangxinjun
 * @createDate 2017年11月24日
 * @updateDate 2017年11月24日
 */
public class SeckillActivityProductModelInformationDTO {
    
    /**
     * 商品型号id
     */
    @ApiModelProperty(value = "商品型号id", required = true)
    private Long productModelId;

    /**
     * 剩余数量
     */
    @ApiModelProperty(value = "剩余数量", required = true)
    private Integer leftCount;

    public Long getProductModelId() {
        return productModelId;
    }

    public void setProductModelId(Long productModelId) {
        this.productModelId = productModelId;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }
}