package com.lawu.eshop.user.query;

import com.lawu.eshop.framework.core.page.PageParam;

import io.swagger.annotations.ApiParam;

public class MerchantInviterParam extends PageParam{
	
	@ApiParam (name="mobileOrName", value = "商家电话或者电话")
	private String mobileOrName;

	public String getMobileOrName() {
		return mobileOrName;
	}

	public void setMobileOrName(String mobileOrName) {
		this.mobileOrName = mobileOrName;
	}

	
	

	
}
