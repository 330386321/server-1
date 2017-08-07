package com.lawu.eshop.ad.srv.bo;

import io.swagger.annotations.ApiModelProperty;

public class AdPlatformVideoBO {
	
	@ApiModelProperty(value = "主键")
	private Long id ;
	
	@ApiModelProperty(value = "广告id")
	private Long adId;
	
	@ApiModelProperty(value = "标题")
	private String title;
	
	@ApiModelProperty(value = "内容")
	private String content;
	
	@ApiModelProperty(value = "商家名称")
	private String name ;
	
	@ApiModelProperty(value = "图片路径")
	private String videoImgUrl ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVideoImgUrl() {
		return videoImgUrl;
	}

	public void setVideoImgUrl(String videoImgUrl) {
		this.videoImgUrl = videoImgUrl;
	}
	
	

}
