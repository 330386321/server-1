package com.lawu.eshop.order.param;

import java.io.Serializable;

import io.swagger.annotations.ApiParam;

public class ShoppingCartUpdateParam implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
    /**
     * 商品型号ID
     */
    @ApiParam(name = "productModelId", required = true, value = "商品型号ID")
    private Long productModelId;

    /**
     * 数量
     */
    @ApiParam(name = "quantity", required = true, value = "数量")
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