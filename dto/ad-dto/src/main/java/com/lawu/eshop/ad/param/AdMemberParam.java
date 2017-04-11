package com.lawu.eshop.ad.param;

import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.OrderTypeEnum;
import com.lawu.eshop.framework.core.page.AbstractPageParam;

import io.swagger.annotations.ApiParam;

public class AdMemberParam extends AbstractPageParam{
	
	@ApiParam (name="typeEnum", value = "AD_TYPE_FLAT 平面 AD_TYPE_VIDEO 视频 AD_TYPE_PRAISE E赞")
	private AdTypeEnum typeEnum;

	@ApiParam (name="longitude1", value = "开始经纬")
	private Double longitude1;
	
	@ApiParam (name="latitude1", value = "结束经纬")
	private Double latitude1;

	@ApiParam (name="orderTypeEnum", value = "AD_TIME_DESC 时间倒序 AD_TORLEPOINT_DESC 总积分倒序  AD_POINT_DESC 单个积分倒序")
	private OrderTypeEnum orderTypeEnum;

	public AdTypeEnum getTypeEnum() {
		return typeEnum;
	}


	public void setTypeEnum(AdTypeEnum typeEnum) {
		this.typeEnum = typeEnum;
	}


	public Double getLongitude1() {
		return longitude1;
	}


	public void setLongitude1(Double longitude1) {
		this.longitude1 = longitude1;
	}


	public Double getLatitude1() {
		return latitude1;
	}


	public void setLatitude1(Double latitude1) {
		this.latitude1 = latitude1;
	}


	public OrderTypeEnum getOrderTypeEnum() {
		return orderTypeEnum;
	}


	public void setOrderTypeEnum(OrderTypeEnum orderTypeEnum) {
		this.orderTypeEnum = orderTypeEnum;
	}
    
    
    

}
