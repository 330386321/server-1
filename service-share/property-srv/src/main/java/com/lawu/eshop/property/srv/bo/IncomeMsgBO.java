package com.lawu.eshop.property.srv.bo;

import java.math.BigDecimal;

public class IncomeMsgBO {
	
	private String userNum;

	private BigDecimal money;

	private Byte type;//类型

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}
}
