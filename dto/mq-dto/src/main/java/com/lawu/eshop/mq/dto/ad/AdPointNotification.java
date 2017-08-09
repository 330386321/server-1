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

	
	
}
