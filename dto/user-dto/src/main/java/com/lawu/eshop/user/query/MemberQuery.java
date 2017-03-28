package com.lawu.eshop.user.query;

import com.lawu.eshop.framework.core.page.PageParam;

import io.swagger.annotations.ApiParam;

public class MemberQuery extends PageParam{

	@ApiParam (name="accountOrNickName", value = "会员账号或者会员昵称")
	private String accountOrNickName;
	

	public String getAccountOrNickName() {
		return accountOrNickName;
	}

	public void setAccountOrNickName(String accountOrNickName) {
		this.accountOrNickName = accountOrNickName;
	}

	
	
}
