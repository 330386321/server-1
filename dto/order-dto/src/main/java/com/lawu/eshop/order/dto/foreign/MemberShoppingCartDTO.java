package com.lawu.eshop.order.dto.foreign;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class MemberShoppingCartDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    private Long id;
    
    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID", required = true)
    private Long merchantId;
    
    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称", required = true)
    private String merchantName;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", required = true)
    private Long productId;

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
    * 商品型号ID
    */
    @ApiModelProperty(value = "商品型号ID", required = true)
    private Long productModelId;
    
    /**
     * 商品型号名称
     */
    @ApiModelProperty(value = "商品型号名称", required = true)
    private String productModelName;

    /**
     * 数量
     */
    @ApiModelProperty(value = "数量", required = true)
    private Integer quantity;
    
    /**
     * 现价
     */
    @ApiModelProperty(value = "现价", required = true)
    private BigDecimal salesPrice;
    
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

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

	public Long getProductModelId() {
		return productModelId;
	}

	public void setProductModelId(Long productModelId) {
		this.productModelId = productModelId;
	}

	public String getProductModelName() {
		return productModelName;
	}

	public void setProductModelName(String productModelName) {
		this.productModelName = productModelName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
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
    
}