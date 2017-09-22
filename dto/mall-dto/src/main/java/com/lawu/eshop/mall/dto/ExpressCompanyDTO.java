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
	 * 快递公司id
	 */
	@ApiModelProperty(value = "快递公司id", required = true)
	private Integer id;
	
	/**
	 * 快递鸟编号
	 */
	@ApiModelProperty(value = "快递鸟编号")
	private String code;
	
	/**
	 * 快递100编号
	 */
	@ApiModelProperty(value = "快递100编号")
	private String kuaidi100Code;
	
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	private String name;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getKuaidi100Code() {
		return kuaidi100Code;
	}

	public void setKuaidi100Code(String kuaidi100Code) {
		this.kuaidi100Code = kuaidi100Code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
