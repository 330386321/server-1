package com.lawu.eshop.user.param;

import java.io.Serializable;

/**
 * api 收货地址操作实体
 * @author zhangrc
 * @date 2017/03/23
 *
 */
public class AddressParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long userId;


	private String name;


	private String mobile;


	private String regionPath;


	private String addr;


	private String postcode;


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getRegionPath() {
		return regionPath;
	}


	public void setRegionPath(String regionPath) {
		this.regionPath = regionPath;
	}


	public String getAddr() {
		return addr;
	}


	public void setAddr(String addr) {
		this.addr = addr;
	}


	public String getPostcode() {
		return postcode;
	}


	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	

}
