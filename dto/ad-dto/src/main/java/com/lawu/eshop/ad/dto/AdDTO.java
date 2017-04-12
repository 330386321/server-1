package com.lawu.eshop.ad.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;

import io.swagger.annotations.ApiModelProperty;

public class AdDTO {
	
	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "商家id")
    private Long merchantId;

	@ApiModelProperty(value = "广告标题")
    private String title;

	@ApiModelProperty(value = "广告附件路径")
    private String mediaUrl;

	@ApiModelProperty(value = "广告内容")
    private String content;

	@ApiModelProperty(value = "广告类型(AD_TYPE_FLAT 平面 AD_TYPE_VIDEO 视频 AD_TYPE_PRAISE E赞)")
    private AdTypeEnum typeEnum;

	@ApiModelProperty(value = "投放方式(PUT_WAY_AREAS 区域 PUT_WAY_FENS 粉丝 PUT_WAY_RADAR 雷达)")
    private PutWayEnum putWayEnum;

	@ApiModelProperty(value = "投放开始时间")
    private Date beginTime;

	@ApiModelProperty(value = "投放所需时间")
    private Date endTime;
	
	@ApiModelProperty(value = "区域")
    private String areas;

	@ApiModelProperty(value = "雷达半径")
    private Integer radius;

	@ApiModelProperty(value = "积分")
    private BigDecimal point;

	@ApiModelProperty(value = "总积分")
    private BigDecimal totalPoint;

	@ApiModelProperty(value = "积分")
    private Integer adCount;

	@ApiModelProperty(value = "状态")
    private AdStatusEnum statusEnum;

	@ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    
	@ApiModelProperty(value = "关注数量")
    private Integer attenCount;
    
	@ApiModelProperty(value = "店铺名称")
    private String name;
    
	@ApiModelProperty(value = "店铺id")
    private Long  merchantStoreId;
    
	@ApiModelProperty(value = "已抢人数")
    private Integer number;


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


	public AdStatusEnum getStatusEnum() {
		return statusEnum;
	}


	public void setStatusEnum(AdStatusEnum statusEnum) {
		this.statusEnum = statusEnum;
	}


	

	public Date getGmtCreate() {
		return gmtCreate;
	}


	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}


	public Integer getAttenCount() {
		return attenCount;
	}


	public void setAttenCount(Integer attenCount) {
		this.attenCount = attenCount;
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


	public Integer getNumber() {
		return number;
	}


	public void setNumber(Integer number) {
		this.number = number;
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

	
	
}
