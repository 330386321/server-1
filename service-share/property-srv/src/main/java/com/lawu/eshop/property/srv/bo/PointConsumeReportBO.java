package com.lawu.eshop.property.srv.bo;

import java.math.BigDecimal;

public class PointConsumeReportBO {
	
	private Long id;
	
	private BigDecimal point;
	
	private String userNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

}
