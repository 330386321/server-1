package com.lawu.eshop.property.dto;

import com.lawu.eshop.property.constants.MemberTransactionCategoryEnum;
import io.swagger.annotations.ApiModelProperty;

public class TransactionDetailH5InfoMemberDTO extends TransactionDetailH5InfoDTO {

    @ApiModelProperty(value = "交易类型")
    private MemberTransactionCategoryEnum transactionCategory;

    public MemberTransactionCategoryEnum getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(MemberTransactionCategoryEnum transactionCategory) {
        this.transactionCategory = transactionCategory;
    }
}
