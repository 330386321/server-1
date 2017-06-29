package com.lawu.eshop.property.srv.bo;

import java.math.BigDecimal;

public class RechargeReportBO {
	
	private Long id;
	
	private BigDecimal rechargeMoney;
	
	private String userNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getRechargeMoney() {
		return rechargeMoney;
	}

	public void setRechargeMoney(BigDecimal rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

}
