package com.lawu.eshop.order.dto.foreign;

import java.math.BigDecimal;

import com.lawu.eshop.order.dto.ShoppingOrderPaymentDTO;

import io.swagger.annotations.ApiModelProperty;

public class ShoppingOrderPaymentForeignDTO extends ShoppingOrderPaymentDTO {

	/**
    * 余额
    */
	@ApiModelProperty(name = "balance", value= "余额", required = true)
    private BigDecimal balance;
	
	/**
	 * 订单总价
	 */
	@ApiModelProperty(value = "订单总价", required = true)
	private BigDecimal orderTotalPrice;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	
}