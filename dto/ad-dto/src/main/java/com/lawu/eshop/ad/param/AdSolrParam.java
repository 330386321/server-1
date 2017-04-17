package com.lawu.eshop.ad.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * @author zhangrc
 * @date 2017/4/13.
 */
public class AdSolrParam extends AbstractPageParam {


    @ApiModelProperty(value = "广告标题")
    private String tilte;
    
    @ApiParam (name="longitude", value = "经度")
	private Double longitude;
	
	@ApiParam (name="latitude", value = "纬度")
	private Double latitude;

	public String getTilte() {
		return tilte;
	}

	public void setTilte(String tilte) {
		this.tilte = tilte;
	}

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

	
    
}
