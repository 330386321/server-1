package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;

public class ShoppingOrderItemExtendBO extends ShoppingOrderItemBO implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * 退款详情
     */
    private ShoppingRefundDetailBO shoppingRefundDetail;
    
    /**
     * 购物订单
     */
    private ShoppingOrderBO shoppingOrder;

	public ShoppingRefundDetailBO getShoppingRefundDetail() {
		return shoppingRefundDetail;
	}

	public void setShoppingRefundDetail(ShoppingRefundDetailBO shoppingRefundDetail) {
		this.shoppingRefundDetail = shoppingRefundDetail;
	}

	public ShoppingOrderBO getShoppingOrder() {
		return shoppingOrder;
	}

	public void setShoppingOrder(ShoppingOrderBO shoppingOrder) {
		this.shoppingOrder = shoppingOrder;
	}
}