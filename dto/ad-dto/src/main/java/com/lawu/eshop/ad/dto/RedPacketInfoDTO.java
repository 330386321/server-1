package com.lawu.eshop.ad.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class RedPacketInfoDTO {
	
	@ApiModelProperty(value = "积分(金额)")
	private BigDecimal point;
	
	@ApiModelProperty(value = "商家logo")
	private String logoUrl;
	
	@ApiModelProperty(value = "商家名称")
	private String name;

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}