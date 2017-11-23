package com.lawu.eshop.product.dto;

import io.swagger.annotations.ApiModelProperty;

public class ProductModelDataDTO {
	
	@ApiModelProperty(value = "型号名称")
	private String name;
	
	@ApiModelProperty(value = "商品图片")
	private String url;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	

}
