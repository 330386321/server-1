package com.lawu.eshop.product.param;

import io.swagger.annotations.ApiParam;

public class ProductParam {

	@ApiParam(value = "商品名称")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
