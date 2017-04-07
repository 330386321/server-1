package com.lawu.eshop.mall.dto.foreign;

import java.io.Serializable;
import java.math.BigDecimal;

import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ShoppingOrderItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品id
	 */
	@ApiModelProperty(value = "商品id", required = true)
	private Long productId;

	/**
	 * 商品名称
	 */
	@ApiModelProperty(value = "商品名称", required = true)
	private String productName;

	/**
	 * 商品型号id
	 */
	@ApiModelProperty(value = "商品型号id", required = true)
	private Long productModelId;

	/**
	 * 商品型号名称
	 */
	@ApiModelProperty(value = "商品型号名称", required = true)
	private String productModelName;

	/**
	 * 商品特征图片
	 */
	@ApiModelProperty(value = "商品特征图片", required = true)
	private String productFeatureImage;

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

	/**
	 * 数量
	 */
	@ApiModelProperty(value = "数量", required = true)
	private Integer quantity;
	
	/**
	 * 订单项状态(0-待付款|1-待发货|2-交易成功|3-交易取消|4-待商家确认|5-待退货|6-待退款|7-退款成功)
	 */
	@ApiModelProperty(value = "订单状态<br/>默认全部<br>PENDING_PAYMENT 待付款<br/>BE_SHIPPED 待发货<br/>TRADING_SUCCESS 交易成功<br/>CANCEL_TRANSACTION 交易取消<br/>TO_BE_CONFIRMED 待商家确认<br/>TO_BE_RETURNED 待退货<br/>TO_BE_REFUNDED 待退款<br/>REFUND_SUCCESSFULLY 退款成功", required = true)
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