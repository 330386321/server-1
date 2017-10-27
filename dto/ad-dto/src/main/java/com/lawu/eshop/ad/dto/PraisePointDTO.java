package com.lawu.eshop.ad.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lawu.eshop.framework.web.json.KeepDecimalJsonSerializer;

import io.swagger.annotations.ApiModelProperty;

public class PraisePointDTO {
	
	@JsonSerialize(using=KeepDecimalJsonSerializer.class)
	@ApiModelProperty(value = "积分")
	private BigDecimal point;

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}
	
	

}
