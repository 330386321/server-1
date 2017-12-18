package com.lawu.eshop.ad.param;

import com.lawu.eshop.ad.constants.AdEgainTypeEnum;
import com.lawu.eshop.framework.core.page.AbstractPageParam;

public class OperatorAdParam extends AbstractPageParam{
	
	private AdEgainTypeEnum adEgainType ;

	public AdEgainTypeEnum getAdEgainType() {
		return adEgainType;
	}

	public void setAdEgainType(AdEgainTypeEnum adEgainType) {
		this.adEgainType = adEgainType;
	}
	
	

}
