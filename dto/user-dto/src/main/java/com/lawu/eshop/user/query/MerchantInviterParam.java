package com.lawu.eshop.user.query;

import com.lawu.eshop.framework.core.page.PageParam;

import io.swagger.annotations.ApiParam;

public class MerchantInviterParam extends PageParam{
	
	@ApiParam (name="account", value = "商家账号")
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	
}
