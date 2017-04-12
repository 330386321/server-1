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
	 * 订单的总状态
	 */
	@ApiModelProperty(value = "订单状态|PENDING_PAYMENT 待付款|BE_SHIPPED 待发货|TO_BE_RECEIVED 待收货|TRADING_SUCCESS 交易成功|CANCEL_TRANSACTION 交易关闭|REFUNDING 退款中", required = true)
	private ShoppingOrderStatusEnum orderStatus;
	
	/**
	 * 是否支持无理由退货,0否 1是
	 */
	@ApiModelProperty(value = "是否支持无理由退货(false 不支持|true 支持)", required = true)
	private Boolean isNoReasonReturn;
	
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

	public Boolean getIsNoReasonReturn() {
		return isNoReasonReturn;
	}

	public void setIsNoReasonReturn(Boolean isNoReasonReturn) {
		this.isNoReasonReturn = isNoReasonReturn;
	}

	public List<ShoppingOrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ShoppingOrderItemDTO> items) {
		this.items = items;
	}

}