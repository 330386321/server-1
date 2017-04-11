package com.lawu.eshop.ad.param;

import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;

import io.swagger.annotations.ApiParam;

public class AdParam {

	@ApiParam (name="title",required = true, value = "广告标题")
    private String title;

	@ApiParam (name="content", value = "广告描述")
    private String content;

	@ApiParam (name="typeEnum",required = true, value = "AD_TYPE_FLAT 平面 AD_TYPE_VIDEO 视频")
    private AdTypeEnum typeEnum;

	@ApiParam (name="putWayEnum",required = true, value = "PUT_WAY_AREAS 区域 PUT_WAY_FENS 粉丝 PUT_WAY_RADAR 雷达")
    private PutWayEnum putWayEnum;

	@ApiParam (name="beginTime",required = true, value = "投放的开始时间")
    private Date beginTime;

	@ApiParam (name="endTime",required = true, value = "投放的结束时间")
    private Date endTime;

	@ApiParam (name="areas", value = "区域 区域最后一位 XX/XX/XX")
    private String areas;

	@ApiParam (name="radius", value = "雷达半径")
    private Integer radius;

	@ApiParam (name="point", value = "单个广告所需积分")
    private BigDecimal point;

	@ApiParam (name="totalPoint",required = true, value = "广告所需总积分")
    private BigDecimal totalPoint;

	@ApiParam (name="adCount", value = "广告数量")
    private Integer adCount;


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	
	public AdTypeEnum getTypeEnum() {
		return typeEnum;
	}


	public void setTypeEnum(AdTypeEnum typeEnum) {
		this.typeEnum = typeEnum;
	}


	public PutWayEnum getPutWayEnum() {
		return putWayEnum;
	}


	public void setPutWayEnum(PutWayEnum putWayEnum) {
		this.putWayEnum = putWayEnum;
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


	public String getAreas() {
		return areas;
	}


	public void setAreas(String areas) {
		this.areas = areas;
	}


	public Integer getRadius() {
		return radius;
	}


	public void setRadius(Integer radius) {
		this.radius = radius;
	}


	public BigDecimal getPoint() {
		return point;
	}


	public void setPoint(BigDecimal point) {
		this.point = point;
	}


	public BigDecimal getTotalPoint() {
		return totalPoint;
	}


	public void setTotalPoint(BigDecimal totalPoint) {
		this.totalPoint = totalPoint;
	}


	public Integer getAdCount() {
		return adCount;
	}


	public void setAdCount(Integer adCount) {
		this.adCount = adCount;
	}



	

}
