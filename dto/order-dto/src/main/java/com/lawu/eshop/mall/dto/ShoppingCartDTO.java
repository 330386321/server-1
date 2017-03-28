package com.lawu.eshop.mall.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class ShoppingCartDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    private Long id;

    /**
     * 用户
     */
    @ApiModelProperty(value = "用户", required = true)
    private Long memberId;

    /**
     * 商家
     */
    @ApiModelProperty(value = "商家", required = true)
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
     * 原价
     */
    @ApiModelProperty(value = "原价", required = true)
    private BigDecimal regularPrice;

    /**
     * 现价
     */
    @ApiModelProperty(value = "现价", required = true)
    private BigDecimal salesPrice;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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

	public BigDecimal getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(BigDecimal regularPrice) {
		this.regularPrice = regularPrice;
	}

	public BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
    
}