package com.lawu.eshop.user.srv.bo;

import java.util.Date;

import com.lawu.eshop.user.dto.MerchantStatusEnum;

public class MerchantInviterBO {
	
	private String account;
	
	private String name;
	
	private String principalName;
	 
	private String principalMobile;
	
	private String regionPath;
	
	private String address;
	
	private Date gmtCreate;
	
	private String path;
	
	private MerchantStatusEnum statusEnum;

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

	public MerchantStatusEnum getStatusEnum() {
		return statusEnum;
	}

	public void setStatusEnum(MerchantStatusEnum statusEnum) {
		this.statusEnum = statusEnum;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
	
	

}
