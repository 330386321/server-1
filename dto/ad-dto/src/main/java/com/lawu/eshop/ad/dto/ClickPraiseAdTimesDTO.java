package com.lawu.eshop.ad.dto;

import io.swagger.annotations.ApiModelProperty;

public class ClickPraiseAdTimesDTO {
	
	@ApiModelProperty(value = "假次数")
	private Integer clickPraiseAdTimes;
	
	@ApiModelProperty(value = "概率数")
	private Integer praiseProb;

	public Integer getClickPraiseAdTimes() {
		return clickPraiseAdTimes;
	}

	public void setClickPraiseAdTimes(Integer clickPraiseAdTimes) {
		this.clickPraiseAdTimes = clickPraiseAdTimes;
	}

	public Integer getPraiseProb() {
		return praiseProb;
	}

	public void setPraiseProb(Integer praiseProb) {
		this.praiseProb = praiseProb;
	}
	
	
	
}
