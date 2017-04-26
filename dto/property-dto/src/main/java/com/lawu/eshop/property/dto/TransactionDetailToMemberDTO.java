package com.lawu.eshop.property.dto;

import java.io.Serializable;

import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * 交易明细DTO
 * To Marchant
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public class TransactionDetailToMemberDTO extends TransactionDetailDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 交易类型
	 */
	@ApiModelProperty(value = "交易类型")
	private MemberTransactionTypeEnum transactionType;

	public MemberTransactionTypeEnum getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(MemberTransactionTypeEnum transactionType) {
		this.transactionType = transactionType;
	}
	
}