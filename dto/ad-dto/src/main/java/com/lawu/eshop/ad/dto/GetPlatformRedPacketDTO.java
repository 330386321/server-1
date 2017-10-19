package com.lawu.eshop.ad.dto;

import java.math.BigDecimal;

public class GetPlatformRedPacketDTO {

	private boolean isGet;
	
	private BigDecimal money;
	
	private boolean isEntity;

	public boolean isGet() {
		return isGet;
	}

	public void setGet(boolean isGet) {
		this.isGet = isGet;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public boolean isEntity() {
		return isEntity;
	}

	public void setEntity(boolean isEntity) {
		this.isEntity = isEntity;
	}
	
	
	
	
}
