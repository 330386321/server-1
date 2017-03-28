package com.lawu.eshop.user.query;

import com.lawu.eshop.framework.core.page.PageParam;

import io.swagger.annotations.ApiParam;

public class FavoriteMerchantParam extends PageParam{
	
	@ApiParam (name="memberId",required = true, value = "会员id")
	private Long memberId;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	
	

}
