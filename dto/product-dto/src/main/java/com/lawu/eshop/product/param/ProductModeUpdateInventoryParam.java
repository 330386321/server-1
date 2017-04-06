package com.lawu.eshop.product.param;

/**
 * Api调用product-srv 更新商品模型的库存参数
 * 
 * @author Sunny
 * @date 2017/4/6
 */
public class ProductModeUpdateInventoryParam {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 数量
	 */
	private Integer quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
