package com.lawu.eshop.property.dto;

import com.lawu.eshop.property.constants.BusinessDepositStatusEnum;

import io.swagger.annotations.ApiModelProperty;

public class BusinessDepositDetailDTO {

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "金额")
	private String amount;

	@ApiModelProperty(value = "状态['PAYING-待支付', 'VERIFY-已支付未核实', 'VERIFYD-已核实', 'APPLY_REFUND-退款申请', 'ACCPET_REFUND-退款已受理', 'REFUND_SUCCESS-退款成功', 'REFUND_FAILURE-退款失败']")
	private BusinessDepositStatusEnum businessDepositStatusEnum;

	@ApiModelProperty(value = "银行名称")
	private String bankName;
	
	@ApiModelProperty(value = "账户名称")
	private String accountName;
	
	@ApiModelProperty(value = "卡号后四位")
	private String cardNo;

	@ApiModelProperty(value = "失败原因")
	private String remark;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
