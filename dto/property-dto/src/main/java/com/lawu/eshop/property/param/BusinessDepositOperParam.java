package com.lawu.eshop.property.param;

import com.lawu.eshop.property.constants.BusinessDepositOperEnum;

import io.swagger.annotations.ApiParam;

public class BusinessDepositOperParam {

	@ApiParam(name = "id", required = true, value = "保证金ID")
	private String id;

	@ApiParam(name = "businessDepositStatusEnum", required = true, value = "操作类型(VERIFYD-核实、ACCPET_REFUND-受理退款、REFUND_SUCCESS-退款成功、REFUND_FAILURE-退款失败)")
	private BusinessDepositOperEnum businessDepositOperEnum;

	@ApiParam(name = "failReason", value = "失败原因")
	private String failReason;

	@ApiParam(name = "userNum", required = true, value = "商家编号")
	private String userNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BusinessDepositOperEnum getBusinessDepositOperEnum() {
		return businessDepositOperEnum;
	}

	public void setBusinessDepositOperEnum(BusinessDepositOperEnum businessDepositOperEnum) {
		this.businessDepositOperEnum = businessDepositOperEnum;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

}
