package com.lawu.eshop.ad.srv.bo;

import java.math.BigDecimal;

public class ClickPointBO {
	
	private BigDecimal point;
	
	private boolean isOverClick;
	
	private boolean sysWords;

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public boolean isOverClick() {
		return isOverClick;
	}

	public void setOverClick(boolean isOverClick) {
		this.isOverClick = isOverClick;
	}

	public boolean isSysWords() {
		return sysWords;
	}

	public void setSysWords(boolean sysWords) {
		this.sysWords = sysWords;
	}
	
	

}
