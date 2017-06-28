package com.lawu.eshop.statistics.param;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class ReportWithdrawParam {
   
	@NotNull(message="money不能为空")
    private BigDecimal money;

	@NotNull(message="userType不能为空")
    private Byte userType;

	@NotNull(message="gmtReport不能为空")
    private Date gmtReport;

	@NotNull(message="gmtCreate不能为空")
    private Date gmtCreate;

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public Date getGmtReport() {
		return gmtReport;
	}

	public void setGmtReport(Date gmtReport) {
		this.gmtReport = gmtReport;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

    
}