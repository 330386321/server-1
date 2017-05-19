package com.lawu.eshop.mq.dto.order;

import java.io.Serializable;

/**
 * 
 * @author Sunny
 * @date 2017年5月19日
 */
public class ShoppingOrderpaymentSuccessfulNotification implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
    * 订单id
    */
    private Long id;
	
    /**
    * 商家编号
    */
    private String merchantNum;
    
    /**
    * 订单编号
    */
    private String orderNum;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
}
