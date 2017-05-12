package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;
import java.util.List;

public class ShoppingRefundDetailExtendBO extends ShoppingRefundDetailBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 退款流程列表
	 */
	private List<ShoppingRefundProcessBO> shoppingRefundProcessList;

	public List<ShoppingRefundProcessBO> getShoppingRefundProcessList() {
		return shoppingRefundProcessList;
	}

	public void setShoppingRefundProcessList(List<ShoppingRefundProcessBO> shoppingRefundProcessList) {
		this.shoppingRefundProcessList = shoppingRefundProcessList;
	}
	
}