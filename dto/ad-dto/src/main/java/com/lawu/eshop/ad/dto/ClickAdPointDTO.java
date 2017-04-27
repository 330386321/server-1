package com.lawu.eshop.ad.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class ClickAdPointDTO {
	
	@ApiModelProperty(value = "今日累计积分")
	private BigDecimal addPoint;
	
	@ApiModelProperty(value = "广告总积分")
	private BigDecimal adTotlePoint;

	public BigDecimal getAddPoint() {
		return addPoint;
	}

	public void setAddPoint(BigDecimal addPoint) {
		this.addPoint = addPoint;
	}

	public BigDecimal getAdTotlePoint() {
		return adTotlePoint;
	}

	public void setAdTotlePoint(BigDecimal adTotlePoint) {
		this.adTotlePoint = adTotlePoint;
	}

	
	

}
