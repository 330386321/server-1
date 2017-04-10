package com.lawu.eshop.ad.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.constants.PutWayEnum;

public class AdDTO {
	
	private Long id;


    private Long merchantId;


    private String title;


    private String mediaUrl;


    private String content;

 
    private AdTypeEnum typeEnum;

  
    private PutWayEnum putWayEnum;

 
    private Date beginTime;

  
    private Date endTime;

    
    private BigDecimal point;

    
    private BigDecimal totalPoint;

    
    private Integer adCount;

    
    private AdStatusEnum statusEnum;

    
    private Date gmtCreate;
    
    private Integer attenCount;


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

	
}