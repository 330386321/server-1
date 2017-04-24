package com.lawu.eshop.ad.param;

import java.math.BigDecimal;

public class AdCommissionJobParam {
	
	private Long memberAdRecordId;		
	private BigDecimal actureMoneyIn;//实际所得余额
	private BigDecimal actureLoveIn; //爱心账户
	private String userNum;			 //用户编号
	private boolean isLast;			 //是否第3级，第三级进积分
	public BigDecimal getActureMoneyIn() {
		return actureMoneyIn;
	}
	public void setActureMoneyIn(BigDecimal actureMoneyIn) {
		this.actureMoneyIn = actureMoneyIn;
	}
	public BigDecimal getActureLoveIn() {
		return actureLoveIn;
	}
	public void setActureLoveIn(BigDecimal actureLoveIn) {
		this.actureLoveIn = actureLoveIn;
	}
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public boolean isLast() {
		return isLast;
	}
	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}
	public Long getMemberAdRecordId() {
		return memberAdRecordId;
	}
	public void setMemberAdRecordId(Long memberAdRecordId) {
		this.memberAdRecordId = memberAdRecordId;
	}
	
}
