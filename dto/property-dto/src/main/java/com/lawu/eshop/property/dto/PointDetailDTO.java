package com.lawu.eshop.property.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class PointDetailDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 积分标题
     */
	@ApiModelProperty(value = "积分标题", required = true)
    private String title;

    /**
     * 积分
     */
	@ApiModelProperty(value = "积分", required = true)
    private Integer point;
	
    /**
     * 积分时间
     */
	@ApiModelProperty(value = "积分时间", required = true)
    private String integralDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getIntegralDate() {
		return integralDate;
	}

	public void setIntegralDate(String integralDate) {
		this.integralDate = integralDate;
	}
	
}