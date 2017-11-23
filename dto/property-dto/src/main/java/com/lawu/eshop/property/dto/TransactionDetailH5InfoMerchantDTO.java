package com.lawu.eshop.property.dto;

import com.lawu.eshop.property.constants.MerchantTransactionCategoryEnum;
import io.swagger.annotations.ApiModelProperty;

public class TransactionDetailH5InfoMerchantDTO extends TransactionDetailH5InfoDTO {

    @ApiModelProperty(value = "交易类型")
    private MerchantTransactionCategoryEnum transactionCategory;

    public MerchantTransactionCategoryEnum getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(MerchantTransactionCategoryEnum transactionCategory) {
        this.transactionCategory = transactionCategory;
    }
}
