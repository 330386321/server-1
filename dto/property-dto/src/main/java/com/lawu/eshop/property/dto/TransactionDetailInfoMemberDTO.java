package com.lawu.eshop.property.dto;

import com.lawu.eshop.property.constants.MemberTransactionCategoryEnum;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import io.swagger.annotations.ApiModelProperty;

public class TransactionDetailInfoMemberDTO extends TransactionDetailBaseInfoDTO {

    @ApiModelProperty(value = "交易分类")
    private MemberTransactionCategoryEnum transactionCategory;

    @ApiModelProperty(value = "交易类型")
    private MemberTransactionTypeEnum memberTransactionTypeEnum;

    public MemberTransactionCategoryEnum getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(MemberTransactionCategoryEnum transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public MemberTransactionTypeEnum getMemberTransactionTypeEnum() {
        return memberTransactionTypeEnum;
    }

    public void setMemberTransactionTypeEnum(MemberTransactionTypeEnum memberTransactionTypeEnum) {
        this.memberTransactionTypeEnum = memberTransactionTypeEnum;
    }
}
