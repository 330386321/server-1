package com.lawu.eshop.property.dto;

import java.math.BigDecimal;

import com.lawu.eshop.property.constants.BusinessDepositStatusEnum;

import io.swagger.annotations.ApiModelProperty;

public class BusinessDepositDetailDTO {

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "状态")
	private BusinessDepositStatusEnum businessDepositStatusEnum;

	@ApiModelProperty(value = "银行名称")
	private String bankName;
	
	@ApiModelProperty(value = "账户名称")
	private String accountName;
	
	@ApiModelProperty(value = "卡号后四位")
	private String cardNo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BusinessDepositStatusEnum getBusinessDepositStatusEnum() {
		return businessDepositStatusEnum;
	}

	public void setBusinessDepositStatusEnum(BusinessDepositStatusEnum businessDepositStatusEnum) {
		this.businessDepositStatusEnum = businessDepositStatusEnum;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

}
