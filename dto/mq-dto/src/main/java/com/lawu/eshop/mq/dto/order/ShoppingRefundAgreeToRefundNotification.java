package com.lawu.eshop.mq.dto.order;

import com.lawu.eshop.compensating.transaction.Notification;
import com.lawu.eshop.mq.dto.order.constants.OrderRefundStatusEnum;
import com.lawu.eshop.mq.dto.order.constants.TransactionPayTypeEnum;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingRefundAgreeToRefundNotification extends Notification {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 购物订单id
	 */
	private Long shoppingOrderId;
	
	/**
	 * 购物订单项id
	 */
	private Long shoppingOrderItemId;
	
    /**
    * 商家编号
    */
    private String merchantNum;
    
    /**
    * 会员编号
    */
    private String menberNum;
    
    /**
     * 退款金额
     */
    private String refundMoney;
    
    /**
     * 支付方式
     */
	private TransactionPayTypeEnum paymentMethod;
	
	/**
	 * 是否是最后一个订单项
	 */
	private boolean isLast;
	
	/**
	 * 第三方支付交易号
	 */
	private String thirdNumber;
	
	/**
	 * 订单是否完成
	 */
	private OrderRefundStatusEnum status;

	public Long getShoppingOrderId() {
		return shoppingOrderId;
	}

	public void setShoppingOrderId(Long shoppingOrderId) {
		this.shoppingOrderId = shoppingOrderId;
	}

	public Long getShoppingOrderItemId() {
		return shoppingOrderItemId;
	}

	public void setShoppingOrderItemId(Long shoppingOrderItemId) {
		this.shoppingOrderItemId = shoppingOrderItemId;
	}

	public String getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}

	public String getMenberNum() {
		return menberNum;
	}

	public void setMenberNum(String menberNum) {
		this.menberNum = menberNum;
	}

	public String getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}

	public TransactionPayTypeEnum getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(TransactionPayTypeEnum paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public boolean getIsLast() {
		return isLast;
	}

	public void setIsLast(boolean isLast) {
		this.isLast = isLast;
	}

	public String getThirdNumber() {
		return thirdNumber;
	}

	public void setThirdNumber(String thirdNumber) {
		this.thirdNumber = thirdNumber;
	}

	public OrderRefundStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderRefundStatusEnum status) {
		this.status = status;
	}
}
