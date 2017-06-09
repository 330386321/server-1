package com.lawu.eshop.order.dto.foreign;

import java.math.BigDecimal;

import com.lawu.eshop.order.dto.ShoppingCartDTO;

import io.swagger.annotations.ApiModelProperty;

public class MemberShoppingCartDTO extends ShoppingCartDTO {
	
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", required = true)
    private String productName;
    
    /**
     * 特征图片
     */
    @ApiModelProperty(value = "特征图片", required = true)
    private String featureImage;
    
    /**
     * 商品型号名称
     */
    @ApiModelProperty(value = "商品型号名称", required = true)
    private String productModelName;

    /**
     * 差价
     */
    @ApiModelProperty(value = "差价(正为涨价,负为降价)", required = true)
    private BigDecimal difference;
    
    /**
     * 是否失效
     */
    @ApiModelProperty(value = "是否失效", required = true)
    private Boolean isInvalid;
    
    /**
    * 库存
    */
    @ApiModelProperty(value = "库存", required = true)
    private Integer inventory;
    
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFeatureImage() {
		return featureImage;
	}

	public void setFeatureImage(String featureImage) {
		this.featureImage = featureImage;
	}

	public String getProductModelName() {
		return productModelName;
	}

	public void setProductModelName(String productModelName) {
		this.productModelName = productModelName;
	}

	public BigDecimal getDifference() {
		return difference;
	}

	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}

	public Boolean getIsInvalid() {
		return isInvalid;
	}

	public void setIsInvalid(Boolean isInvalid) {
		this.isInvalid = isInvalid;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
    
}