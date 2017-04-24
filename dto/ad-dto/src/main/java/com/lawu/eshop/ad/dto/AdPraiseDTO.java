package com.lawu.eshop.ad.dto;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class AdPraiseDTO {

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "广告标题")
	private String title;

	@ApiModelProperty(value = "广告投放开始时间")
	private Date beginTime;

	@ApiModelProperty(value = "广告投放时间")
	private Date endTime;

	@ApiModelProperty(value = "广告总积分")
	private BigDecimal totalPoint;

	@ApiModelProperty(value = "商铺名称")
	private String name;

	@ApiModelProperty(value = "抢赞人数")
	private Integer count;
	
	@ApiModelProperty(value = "店铺id")
	private Long  merchantStoreId;
	
	@ApiModelProperty(name = "logoUrl", value = "logo图片路径")
    private String logoUrl;
	
	@ApiModelProperty(value = "倒计时")
	private Long needBeginTime;
	

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(BigDecimal totalPoint) {
		this.totalPoint = totalPoint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getMerchantStoreId() {
		return merchantStoreId;
	}

	public void setMerchantStoreId(Long merchantStoreId) {
		this.merchantStoreId = merchantStoreId;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Long getNeedBeginTime() {
		return needBeginTime;
	}

	public void setNeedBeginTime(Long needBeginTime) {
		this.needBeginTime = needBeginTime;
	}

	
}
