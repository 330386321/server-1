package com.lawu.eshop.property.param.foreign;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.property.constants.MemberTransactionCategoryEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 交易明细查询参数
 * 
 * @author jiangxinjun
 * @date 2017年10月20日
 */
@ApiModel
public class TransactionDetailQueryForMemberForeignParam extends AbstractPageParam {
	
    @NotNull(message="交易分类不能为空")
	@ApiModelProperty(value = "交易分类", required=true)
	private MemberTransactionCategoryEnum transactionCategory;
    
    @JsonFormat(pattern="yyyy-MM")
    @DateTimeFormat(pattern="yyyy-MM")
    @ApiModelProperty(value = "交易时间")
    private Date date;
	
    public MemberTransactionCategoryEnum getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(MemberTransactionCategoryEnum transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}