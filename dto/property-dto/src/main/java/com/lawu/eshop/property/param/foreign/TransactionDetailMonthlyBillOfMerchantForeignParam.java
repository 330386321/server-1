package com.lawu.eshop.property.param.foreign;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawu.eshop.property.constants.MerchantTransactionCategoryEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 交易明细查询参数
 * 
 * @author jiangxinjun
 * @date 2017年10月20日
 */
@ApiModel
public class TransactionDetailMonthlyBillOfMerchantForeignParam {
	
    @NotNull(message="交易分类不能为空")
	@ApiModelProperty(value = "交易分类", required=true)
	private MerchantTransactionCategoryEnum transactionCategory;
    
    @NotNull(message="交易时间不能为空")
    @JsonFormat(pattern="yyyy-MM")
    @DateTimeFormat(pattern="yyyy-MM")
    @ApiModelProperty(value = "交易时间")
    private Date date;
	
    public MerchantTransactionCategoryEnum getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(MerchantTransactionCategoryEnum transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}