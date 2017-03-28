package com.lawu.eshop.user.srv.bo;

import java.util.Date;

/**
 * 我收藏的商家实体
 * @author zhangrc
 *@date 2017/03/27
 */
public class FavoriteMerchantBO {
	
	private Long id;
	
	private String name;
	
	private String principalName;
	
	private String regionPath;
	
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
