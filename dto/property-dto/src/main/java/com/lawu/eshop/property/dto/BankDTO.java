package com.lawu.eshop.property.dto;

import io.swagger.annotations.ApiModelProperty;

public class BankDTO {
	
	@ApiModelProperty(value = "主键", required = true)
	private Integer id;
	 
	@ApiModelProperty(value = "名称", required = true)
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
