package com.lawu.eshop.user.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class MerchantInviterDTO {
	
	@ApiModelProperty(value = "商家账号", required = true)
	private String account;
	
	@ApiModelProperty(value = "店铺名称", required = true)
	private String name;
	
	@ApiModelProperty(value = "责任人姓名", required = true)
	private String principalName;
	 
	@ApiModelProperty(value = "责任人电话", required = true)
	private String principalMobile;
	
	@ApiModelProperty(value = "区域", required = true)
	private String regionPath;
	
	@ApiModelProperty(value = "详细地址", required = true)
	private String address;
	
	@ApiModelProperty(value = "创建时间", required = true)
	private Date gmtCreate;
	
	@ApiModelProperty(value = "图片", required = true)
	private String path;
	
	@ApiModelProperty(value = "是否审核(0：待审核，1：审核通过，2：审核不通过，3：未提交保证金，4：已提交保证金待财务核实，5：财务审核不通过)", required = true)
	private MerchantStatusEnum statusEnum;
	
	@ApiModelProperty(value = "邀请人数", required = true)
	private Integer inviterCount;

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

	public Integer getInviterCount() {
		return inviterCount;
	}

	public void setInviterCount(Integer inviterCount) {
		this.inviterCount = inviterCount;
	}

	
	

}
