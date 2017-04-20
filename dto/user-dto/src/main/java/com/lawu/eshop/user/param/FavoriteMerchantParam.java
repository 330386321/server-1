package com.lawu.eshop.user.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.user.constants.ManageTypeEnum;

import io.swagger.annotations.ApiParam;

public class FavoriteMerchantParam extends AbstractPageParam{
	
	@ApiParam (name="manageTypeEnum",required = true, value = "ENTITY 实体 COMMON 普通")
	private ManageTypeEnum manageTypeEnum;
	
	@ApiParam (name="longitude", value = "经度")
	private Double longitude;
	
	@ApiParam (name="latitude", value = "纬度")
	private Double latitude;

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public ManageTypeEnum getManageTypeEnum() {
		return manageTypeEnum;
	}

	public void setManageTypeEnum(ManageTypeEnum manageTypeEnum) {
		this.manageTypeEnum = manageTypeEnum;
	}

	
}
