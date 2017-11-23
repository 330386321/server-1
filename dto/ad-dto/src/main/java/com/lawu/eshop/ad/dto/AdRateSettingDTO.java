package com.lawu.eshop.ad.dto;

import io.swagger.annotations.ApiModelProperty;

public class AdRateSettingDTO {
	
	@ApiModelProperty(value = "秒数")
	private Integer gameTime;

	@ApiModelProperty(value = "中奖率(0~100)")
	private Integer rate;

	public Integer getGameTime() {
		return gameTime;
	}

	public void setGameTime(Integer gameTime) {
		this.gameTime = gameTime;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

}
