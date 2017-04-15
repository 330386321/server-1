package com.lawu.eshop.property.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class BusinessDepositSaveDataParam extends BusinessDepositSaveParam {

	@NotNull(message="businessId不能为空")
	private Long businessId;
	
	@NotBlank(message="userNum不能为空")
	private String userNum;
	
	@NotBlank(message="businessAccount不能为空")
	private String businessAccount;
	
	private String deposit;

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	
	public String getBusinessAccount() {
		return businessAccount;
	}

	public void setBusinessAccount(String businessAccount) {
		this.businessAccount = businessAccount;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	
}
