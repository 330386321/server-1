package com.lawu.eshop.user.query;

import com.lawu.eshop.framework.core.page.PageParam;

import io.swagger.annotations.ApiParam;

public class MerchantInviterParam extends PageParam{
	
	@ApiParam (name="inviterId",required = true, value = "邀请人id")
	private Long inviterId;
	
	@ApiParam (name="account", value = "商家账号")
	private String account;

	public Long getInviterId() {
		return inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	
}
