package com.lawu.eshop.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sunny
 * @date 2017/3/31
 */
public class PropertyBalanceDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
    * 余额
    */
	@ApiModelProperty(name = "balance", value= "余额", required = true)
    private BigDecimal balance;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
