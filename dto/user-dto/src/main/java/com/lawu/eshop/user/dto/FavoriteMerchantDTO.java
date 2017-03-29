package com.lawu.eshop.user.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class FavoriteMerchantDTO {
	
	@ApiModelProperty(value = "主键", required = true)
	private Long id;
	
	@ApiModelProperty(value = "店铺名称", required = true)
	private String name;
	
	@ApiModelProperty(value = "责任人姓名", required = true)
	private String principalName;
	
	@ApiModelProperty(value = "区域", required = true)
	private String regionPath;
	
	@ApiModelProperty(value = "创建时间", required = true)
	private Date gmtCreate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRegionPath() {
		return regionPath;
	}

	public void setRegionPath(String regionPath) {
		this.regionPath = regionPath;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}


}
