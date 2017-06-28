package com.lawu.eshop.mall.param.foreign;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class DiscountPackageImageUpdateForeignParam extends DiscountPackageImageSaveForeignParam {
	
	/**
	 * 图片详情id
	 */
	@NotNull
	@ApiModelProperty(name = "id", value = "图片详情id", required = true)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
