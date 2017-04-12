package com.lawu.eshop.ad.dto;

import io.swagger.annotations.ApiModelProperty;

public class FavoriteAdDOViewDTO {

	@ApiModelProperty(value = "主键")
	private Long id;
	
	@ApiModelProperty(value = "广告id")
	private Long AdId;
	
	@ApiModelProperty(value = "商家id")
	private Long merchantId;
	
	@ApiModelProperty(value = "广告标题")
	private String title;
	
	@ApiModelProperty(value = "广告附件路径")
	private String mediaUrl;
	
	@ApiModelProperty(value = "广告内容")
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdId() {
		return AdId;
	}

	public void setAdId(Long adId) {
		AdId = adId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	

}
