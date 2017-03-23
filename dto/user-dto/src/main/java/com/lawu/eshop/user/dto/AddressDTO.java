package com.lawu.eshop.user.dto;

/**
 * 地址DTO转化
 * @author zhangrc
 * @date 2017/3/22
 *
 */
public class AddressDTO {
	private Long id;


	private Boolean isDefault;


	private String name;


	private String mobile;


	private String regionPath;


	private String addr;


	private String postcode;


	public Long getId() {
		return id;
	}
		
		
	public void setId(Long id) {
		this.id = id;
	}
		
		
	public Boolean getIsDefault() {
		return isDefault;
	}
		
		
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
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
