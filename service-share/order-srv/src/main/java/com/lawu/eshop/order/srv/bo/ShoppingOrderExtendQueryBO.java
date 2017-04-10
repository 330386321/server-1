package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;

public class ShoppingOrderExtendQueryBO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 商家ID
	 */
	private Long merchantId;

	/**
	 * 商家名称
	 */
	private String merchantName;

	/**
	 * 运费
	 */
	private BigDecimal freightPrice;

	/**
	 * 订单总价
	 */
	private BigDecimal orderTotalPrice;

	/**
	 * 订单的总状态(0-待付款|1-待发货|2-交易成功|3-交易取消|4-待商家确认|5-待退货|6-待退款|7-退款成功)
	 */
	private ShoppingOrderStatusEnum orderStatus;

	/**
	 * 是否支持无理由退货,0否 1是
	 */
	private Boolean isNoReasonReturn;

	/**
	 * 订单项
	 */
	private List<ShoppingOrderItemBO> items;

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

	public Boolean getIsNoReasonReturn() {
		return isNoReasonReturn;
	}

	public void setIsNoReasonReturn(Boolean isNoReasonReturn) {
		this.isNoReasonReturn = isNoReasonReturn;
	}

	public List<ShoppingOrderItemBO> getItems() {
		return items;
	}

	public void setItems(List<ShoppingOrderItemBO> items) {
		this.items = items;
	}

}