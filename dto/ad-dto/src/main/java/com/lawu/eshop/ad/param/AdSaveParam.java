package com.lawu.eshop.ad.param;

import java.math.BigDecimal;

public class AdSaveParam {
	
	private AdParam adParam;
	
	/**
	 * 商家经度
	 */
	private BigDecimal longitude;

	/**
	 * 商家纬度
	 */
	private BigDecimal latitude;
	
	/**
	 * 商家id
	 */
	private Long merchantId;
	
	/**
	 * 附件路径
	 */
	private String mediaUrl;
	
	/**
	 * 人数
	 */
	private Integer count;
	
	/**
	 * 商家编号
	 */
	private String userNum;
	

	public AdParam getAdParam() {
		return adParam;
	}

	public void setAdParam(AdParam adParam) {
		this.adParam = adParam;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}


	
	

}
