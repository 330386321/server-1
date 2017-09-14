package com.lawu.eshop.mall.dto;

public class AppVersionDTO {
	
	private String appVersion;
	
	private String appBigVersion;
	
	private String updateDesc;
	
	private String downloadUrl;
	
	private Boolean isForce;
	
	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppBigVersion() {
		return appBigVersion;
	}

	public void setAppBigVersion(String appBigVersion) {
		this.appBigVersion = appBigVersion;
	}

	public String getUpdateDesc() {
		return updateDesc;
	}

	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Boolean getIsForce() {
		return isForce;
	}

	public void setIsForce(Boolean isForce) {
		this.isForce = isForce;
	}
}
