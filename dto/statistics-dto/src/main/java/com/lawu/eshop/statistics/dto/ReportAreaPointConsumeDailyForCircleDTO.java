package com.lawu.eshop.statistics.dto;

import java.math.BigDecimal;

public class ReportAreaPointConsumeDailyForCircleDTO {

	private BigDecimal memberPoint;
	
	private BigDecimal merchantPoint;
	
	private BigDecimal memberRechargePoint;
	
	private BigDecimal merchantRechargePoint;
	
	private BigDecimal totalPoint;
	
	private BigDecimal totalRechargePoint;

	public BigDecimal getMemberPoint() {
		return memberPoint;
	}

	public void setMemberPoint(BigDecimal memberPoint) {
		this.memberPoint = memberPoint;
	}

	public BigDecimal getMerchantPoint() {
		return merchantPoint;
	}

	public void setMerchantPoint(BigDecimal merchantPoint) {
		this.merchantPoint = merchantPoint;
	}

	public BigDecimal getMemberRechargePoint() {
		return memberRechargePoint;
	}

	public void setMemberRechargePoint(BigDecimal memberRechargePoint) {
		this.memberRechargePoint = memberRechargePoint;
	}

	public BigDecimal getMerchantRechargePoint() {
		return merchantRechargePoint;
	}

	public void setMerchantRechargePoint(BigDecimal merchantRechargePoint) {
		this.merchantRechargePoint = merchantRechargePoint;
	}

	public BigDecimal getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(BigDecimal totalPoint) {
		this.totalPoint = totalPoint;
	}

	public BigDecimal getTotalRechargePoint() {
		return totalRechargePoint;
	}

	public void setTotalRechargePoint(BigDecimal totalRechargePoint) {
		this.totalRechargePoint = totalRechargePoint;
	}
}
