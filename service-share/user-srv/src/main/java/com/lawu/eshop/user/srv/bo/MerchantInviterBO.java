package com.lawu.eshop.user.srv.bo;

import java.util.Date;

public class MerchantInviterBO {
	
	private String account;
	
	private String name;
	
	private String principalName;
	 
	private String principalMobile;
	
	private String regionPath;
	
	private String address;
	
	private Date gmtCreate;
	
	private Byte status;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getPrincipalMobile() {
		return principalMobile;
	}

	public void setPrincipalMobile(String principalMobile) {
		this.principalMobile = principalMobile;
	}

	public String getRegionPath() {
		return regionPath;
	}

	public void setRegionPath(String regionPath) {
		this.regionPath = regionPath;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
	
	

}
