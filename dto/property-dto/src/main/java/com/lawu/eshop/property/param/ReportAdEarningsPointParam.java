package com.lawu.eshop.property.param;

import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;

public class ReportAdEarningsPointParam {

	private MemberTransactionTypeEnum  MemberTransactionTypeEnum;
	
	private Long bizId;

	public MemberTransactionTypeEnum getMemberTransactionTypeEnum() {
		return MemberTransactionTypeEnum;
	}

	public void setMemberTransactionTypeEnum(MemberTransactionTypeEnum memberTransactionTypeEnum) {
		MemberTransactionTypeEnum = memberTransactionTypeEnum;
	}

	public Long getBizId() {
		return bizId;
	}

	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}

	
	
}
