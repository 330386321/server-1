package com.lawu.eshop.user.query;

import com.lawu.eshop.framework.core.page.PageParam;

public class MemberQuery extends PageParam{

	private String accountOrName;
	
	private Long inviterId;

	public String getAccountOrName() {
		return accountOrName;
	}

	public void setAccountOrName(String accountOrName) {
		this.accountOrName = accountOrName;
	}

	public Long getInviterId() {
		return inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}

	
	
}
