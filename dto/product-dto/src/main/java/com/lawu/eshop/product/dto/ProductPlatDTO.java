package com.lawu.eshop.product.dto;

import io.swagger.annotations.ApiModelProperty;

public class ProductPlatDTO {
	
	@ApiModelProperty(value = "商品ID")
	private Long id;

	@ApiModelProperty(value = "商品名称")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
