package com.lawu.eshop.order.srv.bo;

import java.math.BigDecimal;

public class ShoppingOrderMoneyBO {

    /**
    * 订单总价
    */
    private BigDecimal orderTotalPrice;

    private boolean isActivity;

	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public boolean isActivity() {
		return isActivity;
	}

	public void setActivity(boolean activity) {
		isActivity = activity;
	}
}