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

	/**
	 * 1 积分 2 支付宝 3 微信   积分只针对平面和视频
	 */
	private byte payType;

	/**
	 * 1 平面  2  视频  3 E咻  4 红包
	 */
	private byte adType;

	private String tradeNo;

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
	public byte getPayType() {
		return payType;
	}

	public void setPayType(byte payType) {
		this.payType = payType;
	}

	public byte getAdType() {
		return adType;
	}

	public void setAdType(byte adType) {
		this.adType = adType;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
}
