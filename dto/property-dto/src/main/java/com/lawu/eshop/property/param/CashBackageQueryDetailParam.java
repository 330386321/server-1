package com.lawu.eshop.property.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.property.constants.UserTypeEnum;

import io.swagger.annotations.ApiParam;

public class CashBackageQueryDetailParam  extends AbstractPageParam{

	@ApiParam(name = "account", value = "用户账号")
	private String account;

	@ApiParam(name = "userTypeEnum", required = true, value = "用户类型")
	private UserTypeEnum userTypeEnum;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public UserTypeEnum getUserTypeEnum() {
		return userTypeEnum;
	}

	public void setUserTypeEnum(UserTypeEnum userTypeEnum) {
		this.userTypeEnum = userTypeEnum;
	}

	

}
