package com.lawu.eshop.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 资产余额DTO
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public class PropertyBalanceDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
    * 余额
    */
   private BigDecimal balance;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
