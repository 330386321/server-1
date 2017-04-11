package com.lawu.eshop.ad.param;

import com.lawu.eshop.ad.constants.AdPraiseStatusEnum;
import com.lawu.eshop.framework.core.page.AbstractPageParam;

import io.swagger.annotations.ApiParam;

public class AdPraiseParam extends AbstractPageParam{
	

	@ApiParam (name="statusEnum",required = true, value = "AD_STATUS_SHOOT 开枪中 AD_STATUS_TOBEGIN 即将开始  AD_STATUS_END 已结束")
	private AdPraiseStatusEnum statusEnum;

	public AdPraiseStatusEnum getStatusEnum() {
		return statusEnum;
	}

	public void setStatusEnum(AdPraiseStatusEnum statusEnum) {
		this.statusEnum = statusEnum;
	}


    
    
    

}
