package com.lawu.eshop.property.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.property.constants.UserTypeEnum;

import io.swagger.annotations.ApiParam;

public class CashBackageQueryDetailParam  extends AbstractPageParam{

	@ApiParam(name = "account", value = "用户账号")
	@NotBlank(message="account不能为空")
	private String account;

	@ApiParam(name = "userTypeEnum", required = true, value = "用户类型")
	@NotNull(message="userTypeEnum不能为空")
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
