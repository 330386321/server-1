package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;

public class ShoppingOrderItemBO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品id
	 */
	private Long productId;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 商品型号id
	 */
	private Long productModelId;

	/**
	 * 商品型号名称
	 */
	private String productModelName;

	/**
	 * 商品特征图片
	 */
	private String productFeatureImage;

	/**
	 * 原价
	 */
	private BigDecimal regularPrice;

	/**
	 * 现价
	 */
	private BigDecimal salesPrice;

	/**
	 * 数量
	 */
	private Integer quantity;
	
	/**
	 * 订单项状态(0-待付款|1-待发货|2-交易成功|3-交易取消|4-待商家确认|5-待退货|6-待退款|7-退款成功)
	 */
	private ShoppingOrderStatusEnum orderStatus;

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

	public String getProductFeatureImage() {
		return productFeatureImage;
	}

	public void setProductFeatureImage(String productFeatureImage) {
		this.productFeatureImage = productFeatureImage;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ShoppingOrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ShoppingOrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}