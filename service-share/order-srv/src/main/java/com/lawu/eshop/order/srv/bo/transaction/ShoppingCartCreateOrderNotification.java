package com.lawu.eshop.order.srv.bo.transaction;

import java.util.List;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingCartCreateOrderNotification extends Notification {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 购物订单id
	 */
	private Long shoppingOrderId;
	
	/**
	 * 批量更新库存的参数
	 */
	private List<ProductModeUpdateInventoryBO> params;

	public Long getShoppingOrderId() {
		return shoppingOrderId;
	}

	public void setShoppingOrderId(Long shoppingOrderId) {
		this.shoppingOrderId = shoppingOrderId;
	}

	public List<ProductModeUpdateInventoryBO> getParams() {
		return params;
	}

	public void setParams(List<ProductModeUpdateInventoryBO> params) {
		this.params = params;
	}
}
