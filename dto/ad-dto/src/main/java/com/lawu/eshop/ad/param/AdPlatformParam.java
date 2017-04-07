package com.lawu.eshop.ad.param;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;

import io.swagger.annotations.ApiParam;

public class AdPlatformParam {

	@ApiParam(name = "productId", value = "商品id")
	private Long productId;

	@ApiParam(name = "title", required = true, value = "广告标题")
	private String title;

	@ApiParam(name = "linkUrl", required = true, value = "链接地址")
	private String linkUrl;

	@ApiParam(name = "positionEnum", required = true, value = "")
	private PositionEnum positionEnum;

	@ApiParam(name = "typeEnum", required = true, value = "")
	private TypeEnum typeEnum;

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
