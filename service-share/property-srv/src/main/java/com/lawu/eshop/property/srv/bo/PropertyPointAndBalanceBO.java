package com.lawu.eshop.property.srv.bo;

import java.math.BigDecimal;

public class PropertyPointAndBalanceBO {
	/**
	 * 余额
	 */
	private BigDecimal balance;
	/**
	 * 积分
	 */
	private BigDecimal point;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

}
