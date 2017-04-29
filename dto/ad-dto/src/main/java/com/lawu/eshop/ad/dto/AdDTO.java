package com.lawu.eshop.ad.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawu.eshop.ad.constants.AdPraiseStatusEnum;
import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;


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

	@ApiModelProperty(value = "广告类型(AD_TYPE_FLAT 平面 AD_TYPE_VIDEO 视频 AD_TYPE_PRAISE E赞 AD_TYPE_PACKET 红包)")
    private AdTypeEnum typeEnum;

	@ApiModelProperty(value = "投放方式(PUT_WAY_AREAS 区域 PUT_WAY_FENS 粉丝 PUT_WAY_RADAR 雷达 PUT_WAY_COMMON 普通红包 PUT_WAY_LUCK 手气红包)")
    private PutWayEnum putWayEnum;

	@ApiModelProperty(value = "投放开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginTime;

	@ApiModelProperty(value = "投放所需时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
	
	@ApiModelProperty(value = "区域")
    private String areas;

	@ApiModelProperty(value = "雷达半径")
    private Integer radius;

	@ApiModelProperty(value = "积分")
    private BigDecimal point;

	@ApiModelProperty(value = "总积分")
    private BigDecimal totalPoint;

	@ApiModelProperty(value = "广告数量")
    private Integer adCount;

	@ApiModelProperty(value = "AD_STATUS_DELETE 删除 AD_STATUS_ADD 上架 AD_STATUS_PUTING 投放中  AD_STATUS_PUTED 投放结束"
			+ "AD_STATUS_OUT 下架 AD_STATUS_AUDIT 审核中")
    private AdStatusEnum statusEnum;

	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gmtCreate;
    
	@ApiModelProperty(value = "关注数量")
    private Integer viewCount;
    
	@ApiModelProperty(value = "店铺名称")
    private String name;
    
	@ApiModelProperty(value = "店铺id")
    private Long  merchantStoreId;
    
	@ApiModelProperty(value = "已抢人数")
    private Integer number;
	
	@ApiModelProperty(name = "logoUrl", value = "logo图片路径")
    private String logoUrl;
	
	@ApiModelProperty(value = "倒计时")
	private Long needBeginTime;
	
	@ApiModelProperty(value = "是否收藏")
	private Boolean isFavorite;
	
	@ApiModelProperty(value = "是否抢赞")
	private Boolean isPraise;
	
	@ApiModelProperty(value = "AD_STATUS_SHOOT 开枪中  AD_STATUS_TOBEGIN 即将开始 AD_STATUS_END 已结束")
	private AdPraiseStatusEnum adPraiseStatusEnum;


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

	public Integer getViewCount() {
		return viewCount;
	}


	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
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


	public Boolean getIsFavorite() {
		return isFavorite;
	}


	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}


	public Boolean getIsPraise() {
		return isPraise;
	}


	public void setIsPraise(Boolean isPraise) {
		this.isPraise = isPraise;
	}


	

	
	
}
