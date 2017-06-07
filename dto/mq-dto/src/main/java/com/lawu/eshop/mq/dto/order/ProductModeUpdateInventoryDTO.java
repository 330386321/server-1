package com.lawu.eshop.mq.dto.order;

import java.io.Serializable;

/**
 * @author Sunny
 * @date 2017/4/6
 */
public class ProductModeUpdateInventoryDTO implements Serializable {

	private static final long serialVersionUID = 566121492322667451L;

	/**
	 * 商品模型id
	 */
	private Long prodecutModelId;

	/**
	 * 数量
	 */
	private Integer quantity;

	public Long getProdecutModelId() {
		return prodecutModelId;
	}

	public void setProdecutModelId(Long prodecutModelId) {
		this.prodecutModelId = prodecutModelId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
