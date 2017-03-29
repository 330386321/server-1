package com.lawu.eshop.property.dto;

import io.swagger.annotations.ApiModelProperty;

public class BankAccountDTO {
	
	@ApiModelProperty(value = "主键", required = true)
	private Long id;
	
	@ApiModelProperty(value = "用户名", required = true)
	private String accountName;
	
	@ApiModelProperty(value = "卡号", required = true)
	private String accountNumber;
	
	@ApiModelProperty(value = "支行", required = true)
	private String subBranchName;
	
	@ApiModelProperty(value = "银行名称", required = true)
	private String bankName;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getSubBranchName() {
		return subBranchName;
	}

	public void setSubBranchName(String subBranchName) {
		this.subBranchName = subBranchName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	 

}
