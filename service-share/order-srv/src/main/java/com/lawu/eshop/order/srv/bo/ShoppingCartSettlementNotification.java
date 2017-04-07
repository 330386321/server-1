package com.lawu.eshop.order.srv.bo;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
public class ShoppingCartSettlementNotification extends Notification {

	/**
	 * 产品型号id
	 */
	private Long productModelId;

	/**
	 * 数量
	 */
	private Integer quantity;
	
	public Long getProductModelId() {
		return productModelId;
	}

	public void setProductModelId(Long productModelId) {
		this.productModelId = productModelId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
