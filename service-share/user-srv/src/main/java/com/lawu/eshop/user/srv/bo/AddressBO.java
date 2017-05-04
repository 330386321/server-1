package com.lawu.eshop.user.srv.bo;

public class AddressBO {

 
   private Long id;

  
   private Long userId;


   private Boolean isDefault;


   private String name;


   private String mobile;


   private String regionPath;
   
   private String regionName;


   private String addr;


   private String postcode;


   public Long getId() {
		return id;
   }
	
	
   public void setId(Long id) {
		this.id = id;
   }
	
	
	public Long getUserId() {
		return userId;
	}
	
	
	public void setUserId(Long userId) {
		this.userId = userId;
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


	public String getRegionName() {
		return regionName;
	}


	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	   
   
   
}
