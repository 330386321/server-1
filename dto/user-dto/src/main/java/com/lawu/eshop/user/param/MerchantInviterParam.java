package com.lawu.eshop.user.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import io.swagger.annotations.ApiParam;

public class MerchantInviterParam extends AbstractPageParam{
	
	@ApiParam (name="mobileOrName", value = "商家名称或者电话")
	private String mobileOrName;

	public String getMobileOrName() {
		return mobileOrName;
	}

	public void setMobileOrName(String mobileOrName) {
		this.mobileOrName = mobileOrName;
	}

	
	

	
}
