package com.lawu.eshop.property.dto;

import java.math.BigDecimal;

import com.lawu.eshop.property.constants.CashStatusEnum;

import io.swagger.annotations.ApiModelProperty;

public class WithdrawCashQueryDTO {
	@ApiModelProperty(value = "主键", required = true)
	private Long id;
	@ApiModelProperty(value = "提现时间", required = true)
	private String cdate;
	@ApiModelProperty(value = "提现金额", required = true)
	private BigDecimal cashMoney;
	@ApiModelProperty(value = "状态", required = true)
	private CashStatusEnum cashStatusEnum;
	@ApiModelProperty(value = "标题", required = true)
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
