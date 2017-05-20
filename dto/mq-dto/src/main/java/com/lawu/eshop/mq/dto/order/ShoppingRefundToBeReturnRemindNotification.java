package com.lawu.eshop.mq.dto.order;

import java.io.Serializable;

/**
 * @author Sunny
 * @date 2017/04/18
 */
public class ShoppingRefundToBeReturnRemindNotification implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 购物订单项id
	 */
	private Long shoppingOrderItemId;
	
    /**
    * 商家编号
    */
    private String merchantNum;
    
    /**
     * 用户编号
     */
     private String memberNum;
     
     /**
     * 订单编号
     */
    private String orderNum;
    
    /**
    * 运单编号
    */
    private String waybillNum;

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

	public String getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}
	
}
