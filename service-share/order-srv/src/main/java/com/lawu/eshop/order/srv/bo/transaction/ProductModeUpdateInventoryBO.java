package com.lawu.eshop.order.srv.bo.transaction;

import java.io.Serializable;

/**
 * @author Sunny
 * @date 2017/4/6
 */
public class ProductModeUpdateInventoryBO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品模型id
	 */
	private Long prodecutModelid;

	/**
	 * 数量
	 */
	private Integer quantity;

	public Long getProdecutModelid() {
		return prodecutModelid;
	}

	public void setProdecutModelid(Long prodecutModelid) {
		this.prodecutModelid = prodecutModelid;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
