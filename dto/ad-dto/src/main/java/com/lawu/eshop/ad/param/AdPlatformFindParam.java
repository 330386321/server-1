package com.lawu.eshop.ad.param;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.constants.TypeEnum;
import com.lawu.eshop.framework.core.page.AbstractPageParam;

import io.swagger.annotations.ApiParam;

public class AdPlatformFindParam extends AbstractPageParam{

	@ApiParam(name = "positionEnum", value = "POSITON_RECOMMEND 人气推荐 POSITON_SHOP_TOP 要购物顶部广告 POSITON_SHOP_CHOOSE"
			+ "要购物今日推荐  POSITON_SHOP_GOODS 要购物精品 POSITON_AD_TOP 看广告顶部广告")
	private PositionEnum positionEnum;

	@ApiParam(name = "typeEnum",value = "广告类型")
	private TypeEnum typeEnum;
	
	@ApiParam(name = "title", value = "广告标题")
	private String title;
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
