package com.lawu.eshop.user.param;

import java.io.Serializable;

import io.swagger.annotations.ApiParam;

/**
 * api 收货地址操作实体
 * @author zhangrc
 * @date 2017/03/23
 *
 */
public class AddressParam implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ApiParam (name="name",required = true, value = "收货人")
	private String name;

	@ApiParam (name="mobile",required = true, value = "电话")
	private String mobile;

	@ApiParam (name="regionPath",required = true, value = "地址 格式: 省id/市id/区id")
	private String regionPath;

	@ApiParam (name="addr",required = true, value = "详细地址")
	private String addr;

	@ApiParam (name="postcode",required = true, value = "邮箱")
	private String postcode;


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
