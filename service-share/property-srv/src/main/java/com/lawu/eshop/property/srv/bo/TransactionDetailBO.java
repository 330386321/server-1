package com.lawu.eshop.property.srv.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.property.constants.ConsumptionTypeEnum;

/**
 * 交易明细BO
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public class TransactionDetailBO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 交易标题
	 */
	private String title;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 交易时间
	 */
	private Date transactionDate;

	/**
	 * 消费类型
	 */
	private ConsumptionTypeEnum direction;
	
	/**
	 * 备注
	 */
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