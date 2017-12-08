package com.lawu.eshop.ad.srv.bo;

import java.math.BigDecimal;

public class AdClickPraiseInfoBO {
	
	private BigDecimal point;
	
	private Boolean isPraiseEnd = false;

	public Boolean getIsPraiseEnd() {
		return isPraiseEnd;
	}

	public void setIsPraiseEnd(Boolean isPraiseEnd) {
		this.isPraiseEnd = isPraiseEnd;
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}
	
	

}
