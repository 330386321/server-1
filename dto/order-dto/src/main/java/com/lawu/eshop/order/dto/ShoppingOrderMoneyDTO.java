package com.lawu.eshop.order.dto;

import java.math.BigDecimal;

public class ShoppingOrderMoneyDTO {

    /**
    * 订单总价
    */
    private BigDecimal orderTotalPrice;

	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
}