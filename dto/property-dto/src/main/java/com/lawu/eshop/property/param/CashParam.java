package com.lawu.eshop.property.param;

import io.swagger.annotations.ApiParam;

public class CashParam {

	@ApiParam(name = "cashMoney", required = true, value = "提现金额")
	private String cashMoney;

	@ApiParam(name = "businessBankAccountId", required = true, value = "银行卡账户ID")
	private Long businessBankAccountId;

	@ApiParam(name = "payPwd", required = true, value = "支付密码")
	private String payPwd;

	public String getPayPwd() {
		return payPwd;
	}

	public void setPayPwd(String payPwd) {
		this.payPwd = payPwd;
	}

	public Long getBusinessBankAccountId() {
		return businessBankAccountId;
	}

	public void setBusinessBankAccountId(Long businessBankAccountId) {
		this.businessBankAccountId = businessBankAccountId;
	}

	public String getCashMoney() {
		return cashMoney;
	}

	public void setCashMoney(String cashMoney) {
		this.cashMoney = cashMoney;
	}

}
