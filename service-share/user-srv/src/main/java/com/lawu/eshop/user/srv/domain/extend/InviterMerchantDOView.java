package com.lawu.eshop.user.srv.domain.extend;

import java.io.Serializable;
import java.util.Date;

/**
 * 我邀请的商家
 * @author zhangrc
 * @date 2017/03/30
 *
 */
public class InviterMerchantDOView implements Serializable{


	private static final long serialVersionUID = 1L;
	
	/**
	 * 商家账号
	 */
	private String account;
	
	/**
	 * 商家电话
	 */
	private String mobile; 
	
	/**
	 * 邀请人
	 */
	private Long inviterId;
	
	/**
	 * 店铺名称
	 */
	private String name;
	
	/**
	 * 所在区域
	 */
	private String regionPath;
	
	/**
	 * 负责人名字
	 */
   private String principalName;
   
   /**
    * 负责人手机
    */
   private String principalMobile;
   
   /**
    * 创建时间
    */
   private Date gmtCreate;
   
   /**
    * 是否审核
    */
   private Byte status;
   
   /**
    * 查询条件
    */
   private String mobileAndName;
   

	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRegionPath() {
		return regionPath;
	}
	
	public void setRegionPath(String regionPath) {
		this.regionPath = regionPath;
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMobileAndName() {
		return mobileAndName;
	}

	public void setMobileAndName(String mobileAndName) {
		this.mobileAndName = mobileAndName;
	}

	public Long getInviterId() {
		return inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId = inviterId;
	}
   
   

}
