package com.lawu.eshop.mq.dto.ad;

import com.lawu.eshop.compensating.transaction.Notification;

import java.math.BigDecimal;

/**
 * @author zhangrc
 * @date 2017/4/12
 */
public class AdPointNotification extends Notification {
	
	private static final long serialVersionUID = -1487164000601816456L;
	
	private Long id;
    
	private String userNum;
	
	private BigDecimal point;
	
	private Long adId;
	
	private String regionPath;

	private Byte type;//广告类型，发平面广告和视频广告时用

	private String title;//交易明细后标题
	
	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegionPath() {
		return regionPath;
	}

	public void setRegionPath(String regionPath) {
		this.regionPath = regionPath;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
