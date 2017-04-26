package com.lawu.eshop.order.srv.domain.extend;

import java.io.Serializable;

import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;

public class ShoppingOrderItemExtendDO extends ShoppingOrderItemDO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 退款详情
     */
    private ShoppingRefundDetailDO shoppingRefundDetail;
    
    /**
     * 购物订单
     */
    private ShoppingOrderDO shoppingOrder;

	public ShoppingRefundDetailDO getShoppingRefundDetail() {
		return shoppingRefundDetail;
	}

	public void setShoppingRefundDetail(ShoppingRefundDetailDO shoppingRefundDetail) {
		this.shoppingRefundDetail = shoppingRefundDetail;
	}

	public ShoppingOrderDO getShoppingOrder() {
		return shoppingOrder;
	}

	public void setShoppingOrder(ShoppingOrderDO shoppingOrder) {
		this.shoppingOrder = shoppingOrder;
	}
}