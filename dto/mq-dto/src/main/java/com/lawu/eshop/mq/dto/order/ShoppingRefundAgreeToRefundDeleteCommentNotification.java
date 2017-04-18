package com.lawu.eshop.mq.dto.order;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * @author Sunny
 * @date 2017/04/18
 */
public class ShoppingRefundAgreeToRefundDeleteCommentNotification extends Notification {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 购物订单id
	 */
	private Long shoppingOrderItemId;

	public Long getShoppingOrderItemId() {
		return shoppingOrderItemId;
	}

	public void setShoppingOrderItemId(Long shoppingOrderItemId) {
		this.shoppingOrderItemId = shoppingOrderItemId;
	}
}
