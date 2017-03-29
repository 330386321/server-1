package com.lawu.eshop.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
     * 创建时间
     */
	@ApiModelProperty(value = "创建时间", required = true)
    private Date gmtCreate;

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

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

}