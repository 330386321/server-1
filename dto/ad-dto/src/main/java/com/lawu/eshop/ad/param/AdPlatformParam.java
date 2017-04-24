package com.lawu.eshop.ad.param;

import com.lawu.eshop.ad.constants.TypeEnum;

import io.swagger.annotations.ApiParam;

public class AdPlatformParam {

	@ApiParam(name = "productId", value = "商品id")
	private Long productId;
	
	@ApiParam(name = "merchantStoreId", value = "门店id")
	private Long merchantStoreId;

	@ApiParam(name = "title", required = true, value = "广告标题")
	private String title;

	@ApiParam(name = "linkUrl", required = true, value = "链接地址")
	private String linkUrl;

	@ApiParam(name = "typeEnum", required = true, value = "广告类型")
	private TypeEnum typeEnum;
	
	@ApiParam(name = "content", value = "广告内容")
	private String content;

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

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public TypeEnum getTypeEnum() {
		return typeEnum;
	}

	public void setTypeEnum(TypeEnum typeEnum) {
		this.typeEnum = typeEnum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getMerchantStoreId() {
		return merchantStoreId;
	}

	public void setMerchantStoreId(Long merchantStoreId) {
		this.merchantStoreId = merchantStoreId;
	}



	
}
