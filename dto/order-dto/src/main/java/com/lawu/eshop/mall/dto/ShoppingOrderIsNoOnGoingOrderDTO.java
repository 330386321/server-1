package com.lawu.eshop.mall.dto;

import java.io.Serializable;

public class ShoppingOrderIsNoOnGoingOrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 是否有进行中的订单
	 */
	private Boolean isNoOnGoingOrder;

	public Boolean getIsNoOnGoingOrder() {
		return isNoOnGoingOrder;
	}

	public void setIsNoOnGoingOrder(Boolean isNoOnGoingOrder) {
		this.isNoOnGoingOrder = isNoOnGoingOrder;
	}
	
}