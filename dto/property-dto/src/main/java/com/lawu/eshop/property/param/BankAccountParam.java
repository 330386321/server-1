package com.lawu.eshop.property.param;

import com.lawu.eshop.framework.core.type.UserType;

import io.swagger.annotations.ApiParam;

public class BankAccountParam {
	
	@ApiParam (name="accountName",required = true, value = "开户名")
	private String accountName;
	 
	@ApiParam (name="accountNumber",required = true, value = "银行卡号")
	private String accountNumber;
	 
	@ApiParam (name="bankId",required = true, value = "所属银行")
	private Integer bankId;
	 
	@ApiParam (name="subBranchName",required = true, value = "支行名称")
	private String subBranchName;
	
	@ApiParam (name="userType", value = "用户类型")
	private UserType userType;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getSubBranchName() {
		return subBranchName;
	}

	public void setSubBranchName(String subBranchName) {
		this.subBranchName = subBranchName;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	 
	
	 

}
