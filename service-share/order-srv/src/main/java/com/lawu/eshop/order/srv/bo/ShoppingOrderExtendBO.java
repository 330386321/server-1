package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;
import java.util.List;

public class ShoppingOrderExtendBO extends ShoppingOrderBO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单项
	 */
	private List<ShoppingOrderItemBO> items;

	public List<ShoppingOrderItemBO> getItems() {
		return items;
	}

	public void setItems(List<ShoppingOrderItemBO> items) {
		this.items = items;
	}

}