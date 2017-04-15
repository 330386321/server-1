package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;

public class ShoppingOrderItemEvaluationBO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 购物订单id
	 */
	private Long shoppingOrderItem;
	
    /**
    * 用户ID
    */
    private Long memberId;
	
    /**
    * 商品id
    */
    private Long productId;

	public Long getShoppingOrderItem() {
		return shoppingOrderItem;
	}

	public void setShoppingOrderItem(Long shoppingOrderItem) {
		this.shoppingOrderItem = shoppingOrderItem;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
    
}