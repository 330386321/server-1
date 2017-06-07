package com.lawu.eshop.mq.dto.order;

import com.lawu.eshop.compensating.transaction.Notification;
import com.lawu.eshop.mq.dto.order.constants.TransactionPayTypeEnum;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderTradingSuccessNotification extends Notification {

	private static final long serialVersionUID = 3881842116673637497L;

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
    
    /**
     * 是否是自动收货
     */
    private Boolean isAutoReceipt;
    
    /**
     * 支付方式
     */
    private TransactionPayTypeEnum paymentMethod;
    
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

	public Boolean getIsAutoReceipt() {
		return isAutoReceipt;
	}

	public void setIsAutoReceipt(Boolean isAutoReceipt) {
		this.isAutoReceipt = isAutoReceipt;
	}

	public TransactionPayTypeEnum getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(TransactionPayTypeEnum paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
