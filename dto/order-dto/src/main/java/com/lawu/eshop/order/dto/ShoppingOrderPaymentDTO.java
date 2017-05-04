package com.lawu.eshop.order.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class ShoppingOrderPaymentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 购物订单项id
	 */
	@ApiModelProperty(value = "购物订单项id", required = true)
	private Long id;
	
	/**
	 * 订单编号
	 */
	@ApiModelProperty(value = "订单编号", required = true)
	private String orderNum;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
}