package com.lawu.eshop.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
    private BigDecimal point;
	
    /**
     * 积分时间
     */
	@ApiModelProperty(value = "积分时间", required = true)
    private Date integralDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public Date getIntegralDate() {
		return integralDate;
	}

	public void setIntegralDate(Date integralDate) {
		this.integralDate = integralDate;
	}
	
}