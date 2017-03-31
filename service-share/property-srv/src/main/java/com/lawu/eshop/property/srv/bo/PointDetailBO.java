package com.lawu.eshop.property.srv.bo;

import java.io.Serializable;
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
    private Integer point;

    /**
     * 积分时间
     */
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