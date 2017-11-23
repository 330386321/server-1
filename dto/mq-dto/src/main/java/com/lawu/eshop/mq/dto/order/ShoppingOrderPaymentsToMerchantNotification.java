package com.lawu.eshop.mq.dto.order;

import com.lawu.eshop.compensating.transaction.Notification;
import com.lawu.eshop.mq.dto.order.constants.TransactionPayTypeEnum;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderPaymentsToMerchantNotification extends Notification {

	private static final long serialVersionUID = 4828613333667480720L;

	/**
	 * 购物订单id
	 */
	private Long shoppingOrderId;
	
    /**
    * 商家编号
    */
    private String merchantNum;
    
    /**
    * 商家门店区域（省市区id）
    */
    private String merchantStoreRegionPath;
    
    /**
     * 支付方式
     */
    private TransactionPayTypeEnum paymentMethod;

    private String orderItemProductName;
    
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

	public String getMerchantStoreRegionPath() {
		return merchantStoreRegionPath;
	}

	public void setMerchantStoreRegionPath(String merchantStoreRegionPath) {
		this.merchantStoreRegionPath = merchantStoreRegionPath;
	}

	public TransactionPayTypeEnum getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(TransactionPayTypeEnum paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderItemProductName() {
		return orderItemProductName;
	}

	public void setOrderItemProductName(String orderItemProductName) {
		this.orderItemProductName = orderItemProductName;
	}
}
