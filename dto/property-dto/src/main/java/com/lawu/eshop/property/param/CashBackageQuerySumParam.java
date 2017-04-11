package com.lawu.eshop.property.param;

import com.lawu.eshop.property.constants.UserTypeEnum;

import io.swagger.annotations.ApiParam;

public class CashBackageQuerySumParam {

	@ApiParam(name = "userTypeEnum", required = true, value = "用户类型")
	private UserTypeEnum userTypeEnum;

	public UserTypeEnum getUserTypeEnum() {
		return userTypeEnum;
	}

	public void setUserTypeEnum(UserTypeEnum userTypeEnum) {
		this.userTypeEnum = userTypeEnum;
	}

}
