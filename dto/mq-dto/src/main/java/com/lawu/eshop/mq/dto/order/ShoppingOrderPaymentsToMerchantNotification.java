package com.lawu.eshop.mq.dto.order;

import com.lawu.eshop.compensating.transaction.Notification;
import com.lawu.eshop.mq.dto.order.constants.TransactionPayTypeEnum;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderPaymentsToMerchantNotification extends Notification {

	private static final long serialVersionUID = 4283327727130765097L;

	/**
	 * 购物订单id
	 */
	private Long shoppingOrderId;
	
    /**
    * 商家编号
    */
    private String merchantNum;
    
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

	public TransactionPayTypeEnum getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(TransactionPayTypeEnum paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
