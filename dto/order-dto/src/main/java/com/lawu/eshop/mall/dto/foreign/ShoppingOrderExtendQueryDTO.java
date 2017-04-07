package com.lawu.eshop.mall.dto.foreign;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;

import io.swagger.annotations.ApiModelProperty;

public class ShoppingOrderExtendQueryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "购物订单id", required = true)
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
	 * 运费
	 */
	@ApiModelProperty(value = "运费", required = true)
	private BigDecimal freightPrice;

	/**
	 * 订单总价
	 */
	@ApiModelProperty(value = "订单总价", required = true)
	private BigDecimal orderTotalPrice;

	/**
	 * 订单的总状态(0-待付款|1-待发货|2-交易成功|3-交易取消|4-待商家确认|5-待退货|6-待退款|7-退款成功)
	 */
	@ApiModelProperty(value = "订单状态<br/>默认全部<br>PENDING_PAYMENT 待付款<br/>BE_SHIPPED 待发货<br/>TRADING_SUCCESS 交易成功<br/>CANCEL_TRANSACTION 交易取消<br/>TO_BE_CONFIRMED 待商家确认<br/>TO_BE_RETURNED 待退货<br/>TO_BE_REFUNDED 待退款<br/>REFUND_SUCCESSFULLY 退款成功", required = true)
	private ShoppingOrderStatusEnum orderStatus;

	/**
	 * 订单项
	 */
	@ApiModelProperty(value = "订单项", required = true)
	private List<ShoppingOrderItemDTO> items;

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

	public BigDecimal getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(BigDecimal freightPrice) {
		this.freightPrice = freightPrice;
	}

	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public ShoppingOrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ShoppingOrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<ShoppingOrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ShoppingOrderItemDTO> items) {
		this.items = items;
	}

}