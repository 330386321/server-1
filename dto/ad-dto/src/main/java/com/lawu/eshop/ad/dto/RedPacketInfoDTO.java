package com.lawu.eshop.ad.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class RedPacketInfoDTO {
	
	@ApiModelProperty(value = "积分(金额)")
	private BigDecimal point;
	
	@ApiModelProperty(value = "商家logo")
	private String logoUrl;
	
	@ApiModelProperty(value = "商家名称")
	private String name;
	
	@ApiModelProperty(value = "商家账号")
	private String inviterAccount;
	
	@ApiModelProperty(value = "广告附件路径")
	private String mediaUrl;

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInviterAccount() {
		return inviterAccount;
	}

	public void setInviterAccount(String inviterAccount) {
		this.inviterAccount = inviterAccount;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	
	

}
