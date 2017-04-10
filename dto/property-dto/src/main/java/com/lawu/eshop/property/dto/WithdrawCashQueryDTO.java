package com.lawu.eshop.property.dto;

import java.math.BigDecimal;

import com.lawu.eshop.property.constants.CashStatusEnum;

public class WithdrawCashQueryDTO {
	private Long id;
	private String cdate;
	private BigDecimal cashMoney;
	private CashStatusEnum cashStatusEnum;
	private String title;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public BigDecimal getCashMoney() {
		return cashMoney;
	}
	public void setCashMoney(BigDecimal cashMoney) {
		this.cashMoney = cashMoney;
	}
	public CashStatusEnum getCashStatusEnum() {
		return cashStatusEnum;
	}
	public void setCashStatusEnum(CashStatusEnum cashStatusEnum) {
		this.cashStatusEnum = cashStatusEnum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
