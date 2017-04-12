package com.lawu.eshop.property.param;

import com.lawu.eshop.property.constants.UserTypeEnum;

/**
 * 
 * <p>
 * Description: app调用支付宝前请求参数
 * </p>
 * @author Yangqh
 * @date 2017年4月6日 下午5:36:40
 *
 */
public class ThirdPayDataParam extends ThirdPayParam {
	
	/**
	 * 用户类型
	 */
	private UserTypeEnum userTypeEnum;
	
	/**
	 * 用户编号
	 */
	private String userNum;
	
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	
	/**
	 * 主题
	 */
	private String subject;
	
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public UserTypeEnum getUserTypeEnum() {
		return userTypeEnum;
	}
	public void setUserTypeEnum(UserTypeEnum userTypeEnum) {
		this.userTypeEnum = userTypeEnum;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}