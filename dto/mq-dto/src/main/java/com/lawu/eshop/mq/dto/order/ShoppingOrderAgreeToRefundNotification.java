package com.lawu.eshop.mq.dto.order;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderAgreeToRefundNotification extends Notification {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 购物订单id
	 */
	private Long shoppingOrderId;
	
    /**
    * 商家编号
    */
    private String merchantNum;
    
    /**
     * 订单总价
     */
    private String orderTotalPrice;

	public Long getShoppingOrderId() {
		return shoppingOrderId;
	}

	public void setShoppingOrderId(Long shoppingOrderId) {
		this.shoppingOrderId = shoppingOrderId;
	}

	public String getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}

	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
}
