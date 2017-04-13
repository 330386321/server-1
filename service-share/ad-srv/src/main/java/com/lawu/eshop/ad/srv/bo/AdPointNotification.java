package com.lawu.eshop.ad.srv.bo;

import java.math.BigDecimal;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * @author zhangrc
 * @date 2017/4/12
 */
public class AdPointNotification extends Notification {
    
	private String userNum;
	
	private BigDecimal point;
	

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

	
	
}
