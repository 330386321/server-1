package com.lawu.eshop.user.dto;

import java.util.Date;

import com.lawu.eshop.user.constants.FansInviteResultEnum;

import io.swagger.annotations.ApiModelProperty;

public class FansInviteContentDTO {

	@ApiModelProperty(value = "主键", required = true)
	private Long id;

	@ApiModelProperty(value = "商家主键", required = true)
	private Long merchantId;
	
	@ApiModelProperty(value = "商家编号", required = true)
	private String merchantNum;
	
	@ApiModelProperty(value = "商家邀请内容的图片", required = true)
	private String url;
	
	@ApiModelProperty(value = "商家logo", required = true)
	private String logoUrl;
	
	@ApiModelProperty(value = "商家门店名称", required = true)
	private String merchantStoreName;
	
	@ApiModelProperty(value = "商家邀请的内容", required = true)
	private String inviteContent;
	
	@ApiModelProperty(value = "商家简介", required = true)
	private String merchantStoreIntro;
	
	private Long fansInviteDetailId;
	
	@ApiModelProperty(value = "创建时间", required = true)
	private Date gmtCreate;
	
	@ApiModelProperty(value = "是否处理过, NOT_DEAL--未处理，AGREE--同意，REFUSE--拒绝", required = true)
	private FansInviteResultEnum fansInviteResultEnum;
	
	private Date gmtModified;
	
	public FansInviteResultEnum getFansInviteResultEnum() {
		return fansInviteResultEnum;
	}

	public void setFansInviteResultEnum(FansInviteResultEnum fansInviteResultEnum) {
		this.fansInviteResultEnum = fansInviteResultEnum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getMerchantStoreName() {
		return merchantStoreName;
	}

	public void setMerchantStoreName(String merchantStoreName) {
		this.merchantStoreName = merchantStoreName;
	}

	public String getInviteContent() {
		return inviteContent;
	}

	public void setInviteContent(String inviteContent) {
		this.inviteContent = inviteContent;
	}

	public String getMerchantStoreIntro() {
		return merchantStoreIntro;
	}

	public void setMerchantStoreIntro(String merchantStoreIntro) {
		this.merchantStoreIntro = merchantStoreIntro;
	}

	public Long getFansInviteDetailId() {
		return fansInviteDetailId;
	}

	public void setFansInviteDetailId(Long fansInviteDetailId) {
		this.fansInviteDetailId = fansInviteDetailId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
}