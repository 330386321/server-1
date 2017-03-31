package com.lawu.eshop.property.srv.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
    private String transactionDate;

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

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	
}