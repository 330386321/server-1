package com.lawu.eshop.mall.param;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiParam;

public class ShoppingCartParam implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
    /**
     * 用户ID
     */
    @ApiParam(name = "memberId", required = true, value = "用户ID")
    private Long memberId;

    /**
     * 商家ID
     */
    @ApiParam(name = "merchantId", required = true, value = "商家ID")
    private Long merchantId;

    /**
     * 商家名称
     */
    @ApiParam(name = "merchantName", required = true, value = "商家名称")
    private String merchantName;

    /**
     * 商品ID
     */
    @ApiParam(name = "productId", required = true, value = "商品ID")
    private Long productId;

    /**
     * 商品名称
     */
    @ApiParam(name = "productId", required = true, value = "商品名称")
    private String productName;

    /**
     * 商品型号ID
     */
    @ApiParam(name = "productModelId", required = true, value = "商品型号ID")
    private Long productModelId;

    /**
     * 商品型号名称
     */
    @ApiParam(name = "productModelName", required = true, value = "商品型号名称")
    private String productModelName;

    /**
     * 数量
     */
    @ApiParam(name = "quantity", required = true, value = "数量")
    private Integer quantity;

    /**
     * 原价
     */
    @ApiParam(name = "regularPrice", required = true, value = "原价")
    private BigDecimal regularPrice;

    /**
     * 现价
     */
    @ApiParam(name = "salesPrice", required = true, value = "现价")
    private BigDecimal salesPrice;

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