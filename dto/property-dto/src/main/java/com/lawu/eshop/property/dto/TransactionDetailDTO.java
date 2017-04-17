package com.lawu.eshop.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.property.constants.ConsumptionTypeEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * 交易明细DTO
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public class TransactionDetailDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 交易标题
     */
	@ApiModelProperty(value = "交易标题", required = true)
    private String title;

    /**
     * 金额
     */
	@ApiModelProperty(value = "金额", required = true)
    private BigDecimal amount;

    /**
     * 交易时间
     */
	@ApiModelProperty(value = "交易时间", required = true)
    private Date transactionDate;
	
	/**
	 * 消费类型
	 */
	@ApiModelProperty(value = "消费类型", required = true)
	private ConsumptionTypeEnum direction;
	
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注", required = true)
	private String remark;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public ConsumptionTypeEnum getDirection() {
		return direction;
	}

	public void setDirection(ConsumptionTypeEnum direction) {
		this.direction = direction;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}