package com.lawu.eshop.mq.dto.property;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * 资产模块支付购物订单成功，发送给订单模块的数据
 * 
 * @author Sunny
 * @date 2017年4月14日
 */
public class ShoppingOrderPaymentNotification extends Notification {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 购物订单id
	 */
	private Long shoppingOrderId;

	public Long getShoppingOrderId() {
		return shoppingOrderId;
	}

	public void setShoppingOrderId(Long shoppingOrderId) {
		this.shoppingOrderId = shoppingOrderId;
	}
	
}
