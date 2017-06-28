package com.lawu.eshop.ad.dto;

import com.lawu.eshop.ad.constants.AdTypeEnum;

import io.swagger.annotations.ApiModelProperty;

public class AdSolrDTO {
	
	@ApiModelProperty(value = "主键")
	private Long id;
	
	@ApiModelProperty(value = "广告标题")
    private String title;

	@ApiModelProperty(value = "广告附件路径")
    private String mediaUrl;

	@ApiModelProperty(value = "广告内容")
    private String content;
	
	@ApiModelProperty(value = "关注人数")
    private int count;
	
	@ApiModelProperty(value = "广告类型(AD_TYPE_FLAT 平面 AD_TYPE_VIDEO 视频 )")
    private AdTypeEnum typeEnum;
	
	@ApiModelProperty(value = "视频封面图片路径")
	private String videoImgUrl;

	public AdTypeEnum getTypeEnum() {
		return typeEnum;
	}

	public void setTypeEnum(AdTypeEnum typeEnum) {
		this.typeEnum = typeEnum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getVideoImgUrl() {
		return videoImgUrl;
	}

	public void setVideoImgUrl(String videoImgUrl) {
		this.videoImgUrl = videoImgUrl;
	}

	



	
	
}
