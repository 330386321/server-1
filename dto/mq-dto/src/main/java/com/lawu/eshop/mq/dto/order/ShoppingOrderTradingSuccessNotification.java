package com.lawu.eshop.mq.dto.order;

import com.lawu.eshop.compensating.transaction.Notification;
import com.lawu.eshop.mq.dto.order.constants.TransactionPayTypeEnum;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingOrderTradingSuccessNotification extends Notification {

	private static final long serialVersionUID = -609446919349304578L;

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

	/**
	 * 订单号（资金冻结表新增订单号字段）
	 */
	private String orderNum;

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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderItemProductName() {
		return orderItemProductName;
	}

	public void setOrderItemProductName(String orderItemProductName) {
		this.orderItemProductName = orderItemProductName;
	}
}
