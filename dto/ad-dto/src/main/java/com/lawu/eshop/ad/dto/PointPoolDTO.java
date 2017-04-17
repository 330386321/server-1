package com.lawu.eshop.ad.dto;

import java.math.BigDecimal;

public class PointPoolDTO {
	
	private Long memberId;
	
	private BigDecimal point;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}
	
	

}
