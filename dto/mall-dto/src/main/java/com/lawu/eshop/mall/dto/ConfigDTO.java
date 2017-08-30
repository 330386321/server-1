package com.lawu.eshop.mall.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
public class ConfigDTO {

    @ApiModelProperty(value = "视频URL")
    private String videoUrl;

    @ApiModelProperty(value = "图片URL")
    private String imageUrl;
    
    @ApiModelProperty(value = "邀请商家URL")
    private String inviterMerchantUrl;
    
    @ApiModelProperty(value = "邀请会员URL")
    private String inviterMemberUrl;
    
    @ApiModelProperty(value = "h5Ip")
    private String memberH5Ip;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

	public String getInviterMerchantUrl() {
		return inviterMerchantUrl;
	}

	public void setInviterMerchantUrl(String inviterMerchantUrl) {
		this.inviterMerchantUrl = inviterMerchantUrl;
	}

	public String getInviterMemberUrl() {
		return inviterMemberUrl;
	}

	public void setInviterMemberUrl(String inviterMemberUrl) {
		this.inviterMemberUrl = inviterMemberUrl;
	}

	public String getMemberH5Ip() {
		return memberH5Ip;
	}

	public void setMemberH5Ip(String memberH5Ip) {
		this.memberH5Ip = memberH5Ip;
	}
    
    
}
