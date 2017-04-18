package com.lawu.eshop.property.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.lawu.eshop.property.constants.CashOperEnum;

import io.swagger.annotations.ApiParam;

public class CashBackageOperParam {

	@ApiParam(name = "ids",required = true, value = "提现ID(多条记录用英文逗号分隔)")
	@NotBlank(message="ids不能为空")
	private String ids;
	
	@ApiParam(name = "cashOperEnum", required = true, value = "操作类型(ACCEPT-受理；SUCCESS-成功；FAILURE-失败)")
	@NotNull(message="cashOperEnum不能为空")
	private CashOperEnum cashOperEnum;

	@ApiParam(name = "auditFailReason", value = "失败原因")
	private String auditFailReason;
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public CashOperEnum getCashOperEnum() {
		return cashOperEnum;
	}

	public void setCashOperEnum(CashOperEnum cashOperEnum) {
		this.cashOperEnum = cashOperEnum;
	}

	public String getAuditFailReason() {
		return auditFailReason;
	}

	public void setAuditFailReason(String auditFailReason) {
		this.auditFailReason = auditFailReason;
	}

}
