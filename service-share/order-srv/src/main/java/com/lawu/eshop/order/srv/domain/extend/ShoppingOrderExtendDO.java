package com.lawu.eshop.order.srv.domain.extend;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;

public class ShoppingOrderExtendDO extends ShoppingOrderDO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**********************************
	 * Extend Attributes
	 ************************************/
	/**
	 * 订单项
	 */
	private List<ShoppingOrderItemDO> items;
	
	/**
	 * 用于更新支付给商家的实际金额
	 */
	private BigDecimal actualAmountSubtraction;

	public List<ShoppingOrderItemDO> getItems() {
		return items;
	}

	public void setItems(List<ShoppingOrderItemDO> items) {
		this.items = items;
	}

	public BigDecimal getActualAmountSubtraction() {
		return actualAmountSubtraction;
	}

	public void setActualAmountSubtraction(BigDecimal actualAmountSubtraction) {
		this.actualAmountSubtraction = actualAmountSubtraction;
	}
	
}