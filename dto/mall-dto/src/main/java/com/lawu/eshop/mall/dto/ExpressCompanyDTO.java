package com.lawu.eshop.mall.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 快递公司BO
 *
 * @author Sunny
 * @date 2017/3/24
 */
public class ExpressCompanyDTO {

	
	/**
	 * 编码
	 */
	@ApiModelProperty(value = "编码", required = true)
	private String code;
	
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	private String name;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
