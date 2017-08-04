package com.lawu.eshop.ad.srv.bo;

import com.lawu.eshop.ad.constants.AdEgainTypeEnum;
import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.ManageTypeEnum;
import com.lawu.eshop.ad.constants.RelateTypeEnum;

import io.swagger.annotations.ApiModelProperty;

public class AdEgainDetailBO {

    /**
    * 主键
    */
    private Long id;
	
	/**
	 * 商家门店id
	 */
	private Long merchantStoreId;

	/**
	 * 店铺名称
	 */
	private String merchantStoreName;

	/**
	 * 经营类型
	 */
	private ManageTypeEnum manageType;

	/**
	 * 名称 ad.title
	 */
	private String title;

	/**
	 * 门店logo
	 */
	private String logoUrl;

	/**
	 * 广附件路径
	 */
	private String mediaUrl;

	/**
	 * 视频封面图片路径
	 */
	private String videoImgUrl;

	/**
	 * 广告内容 ad.content
	 */
	private String content;

	/**
	 * 广告类型
	 */
	private AdEgainTypeEnum type;

	/**
	 * 广告浏览次数
	 */
	private Integer viewCount;

	/**
	 * 是否收藏
	 */
	private Boolean isFavorite;
	
	
	private Boolean isClickAd;
	
	private AdStatusEnum statusEnum;
	 
	private Long merchantId;
	
	private Long productId;
	
	private RelateTypeEnum relateType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantStoreId() {
		return merchantStoreId;
	}

	public void setMerchantStoreId(Long merchantStoreId) {
		this.merchantStoreId = merchantStoreId;
	}

	public String getMerchantStoreName() {
		return merchantStoreName;
	}

	public void setMerchantStoreName(String merchantStoreName) {
		this.merchantStoreName = merchantStoreName;
	}

	public ManageTypeEnum getManageType() {
		return manageType;
	}

	public void setManageType(ManageTypeEnum manageType) {
		this.manageType = manageType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getVideoImgUrl() {
		return videoImgUrl;
	}

	public void setVideoImgUrl(String videoImgUrl) {
		this.videoImgUrl = videoImgUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public AdEgainTypeEnum getType() {
		return type;
	}

	public void setType(AdEgainTypeEnum type) {
		this.type = type;
	}

	public Boolean getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Boolean getIsClickAd() {
		return isClickAd;
	}

	public void setIsClickAd(Boolean isClickAd) {
		this.isClickAd = isClickAd;
	}

	public AdStatusEnum getStatusEnum() {
		return statusEnum;
	}

	public void setStatusEnum(AdStatusEnum statusEnum) {
		this.statusEnum = statusEnum;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public RelateTypeEnum getRelateType() {
		return relateType;
	}

	public void setRelateType(RelateTypeEnum relateType) {
		this.relateType = relateType;
	}
	
	
	

}
