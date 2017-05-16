package com.lawu.eshop.property.dto;

import com.lawu.eshop.property.constants.PropertyinfoFreezeEnum;

import io.swagger.annotations.ApiModelProperty;

public class PropertyinfoFreezeInfoDTO {

	@ApiModelProperty(value = "用户编号")
	private String userNum;
	
	@ApiModelProperty(value = "是否冻结(NO-否、YES-是)")
	private PropertyinfoFreezeEnum freeze;

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public PropertyinfoFreezeEnum getFreeze() {
		return freeze;
	}

	public void setFreeze(PropertyinfoFreezeEnum freeze) {
		this.freeze = freeze;
	}

}
