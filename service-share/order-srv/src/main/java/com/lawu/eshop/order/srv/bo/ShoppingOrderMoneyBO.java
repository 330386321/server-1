package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShoppingOrderMoneyBO implements Serializable {

	private static final long serialVersionUID = 1L;

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