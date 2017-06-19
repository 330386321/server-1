package com.lawu.eshop.order.srv.bo;

import java.math.BigDecimal;

public class ShoppingOrderMoneyBO {

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