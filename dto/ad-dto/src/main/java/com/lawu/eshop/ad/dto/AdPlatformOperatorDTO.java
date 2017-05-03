package com.lawu.eshop.ad.dto;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;

import io.swagger.annotations.ApiModelProperty;

public class AdPlatformOperatorDTO {

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "商品id")
	private Long productId;

	@ApiModelProperty(value = "广告标题")
	private String title;

	@ApiModelProperty(value = "广告附件地址")
	private String mediaUrl;

	@ApiModelProperty(value = "链接地址")
	private String linkUrl;
	
	@ApiModelProperty(value = "广告内容")
	private String content;

	@ApiModelProperty(value = "门店ID")
	private Long merchantStoreId;
	
	@ApiModelProperty(value = "门店名称")
	private String merchantName;
	
	@ApiModelProperty(value = "商品名称")
	private String productName;
	
	@ApiModelProperty(value = "POSITON_RECOMMEND 人气推荐 POSITON_SHOP_TOP 要购物顶部广告 POSITON_SHOP_CHOOSE"
			+ "要购物今日推荐  POSITON_SHOP_GOODS 要购物精品 POSITON_AD_TOP 看广告顶部广告 SHOPPING_BUY E店必够 SHOPPING_GOODS  特色好货  SHOPPING_BENEFIT 实惠单品")
	private PositionEnum positionEnum;

	@ApiModelProperty(value = "广告类型")
	private TypeEnum typeEnum;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Long getMerchantStoreId() {
		return merchantStoreId;
	}

	public void setMerchantStoreId(Long merchantStoreId) {
		this.merchantStoreId = merchantStoreId;
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public PositionEnum getPositionEnum() {
		return positionEnum;
	}

	public void setPositionEnum(PositionEnum positionEnum) {
		this.positionEnum = positionEnum;
	}

	public TypeEnum getTypeEnum() {
		return typeEnum;
	}

	public void setTypeEnum(TypeEnum typeEnum) {
		this.typeEnum = typeEnum;
	}
	
	
}
