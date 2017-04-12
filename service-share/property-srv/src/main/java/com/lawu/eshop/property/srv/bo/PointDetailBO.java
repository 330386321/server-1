package com.lawu.eshop.property.srv.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PointDetailBO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 积分标题
     */
    private String title;

    /**
     * 积分
     */
    private BigDecimal point;

    /**
     * 积分时间
     */
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