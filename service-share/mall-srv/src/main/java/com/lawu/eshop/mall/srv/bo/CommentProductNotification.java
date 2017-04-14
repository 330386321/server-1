package com.lawu.eshop.mall.srv.bo;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
public class CommentProductNotification extends Notification{

	private static final long serialVersionUID = 1L;
	
	private Long shoppingOrderItemId;

	public Long getShoppingOrderItemId() {
		return shoppingOrderItemId;
	}

	public void setShoppingOrderItemId(Long shoppingOrderItemId) {
		this.shoppingOrderItemId = shoppingOrderItemId;
	}
	
}
