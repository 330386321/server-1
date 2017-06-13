package com.lawu.eshop.ad.dto;

import io.swagger.annotations.ApiModelProperty;

public class ClickPraiseAdTimesDTO {
	
	@ApiModelProperty(value = "概率数")
	private Integer clickPraiseAdTimes;

	public Integer getClickPraiseAdTimes() {
		return clickPraiseAdTimes;
	}

	public void setClickPraiseAdTimes(Integer clickPraiseAdTimes) {
		this.clickPraiseAdTimes = clickPraiseAdTimes;
	}
	
	

}
