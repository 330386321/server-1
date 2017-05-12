package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;

public class ShoppingOrderItemExtendBO extends ShoppingOrderItemBO implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * 退款详情
     */
    private ShoppingRefundDetailExtendBO shoppingRefundDetail;
    
    /**
     * 购物订单
     */
    private ShoppingOrderBO shoppingOrder;

	public ShoppingRefundDetailExtendBO getShoppingRefundDetail() {
		return shoppingRefundDetail;
	}

	public void setShoppingRefundDetail(ShoppingRefundDetailExtendBO shoppingRefundDetail) {
		this.shoppingRefundDetail = shoppingRefundDetail;
	}

	public ShoppingOrderBO getShoppingOrder() {
		return shoppingOrder;
	}

	public void setShoppingOrder(ShoppingOrderBO shoppingOrder) {
		this.shoppingOrder = shoppingOrder;
	}
}