package com.lawu.eshop.property.param;

import java.util.Date;

public class CashDataParam extends CashParam{

	/**
	 * 用户编号
	 */
	private String userNum;

	/**
	 * 提现类型(1-用户提现2-商家提现)
	 */
	private Byte userType;

	/**
	 * 提现单号
	 */
	private String cashNumber;
	
	/**
	 * 交易类型
	 */
	private Byte transactionType;
	
	/**
	 * 交易账户
	 */
	private String account;
	
	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Byte getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Byte transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * withdraw_cash.gmt_create
	 */
	private Date gmtCreate;

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public String getCashNumber() {
		return cashNumber;
	}

	public void setCashNumber(String cashNumber) {
		this.cashNumber = cashNumber;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

}
