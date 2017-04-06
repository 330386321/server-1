package com.lawu.eshop.property.param;

import java.io.Serializable;

import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
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
public class AppAlipayParam implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 金额
	 */
	private String totalAmount;
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	/**
	 * 交易标题
	 */
	private String subject;
	/**
	 * 用户类型
	 */
	private UserTypeEnum userTypeEnum;
	
	/**
	 * 业务表ID(支持多个订单,用英文逗号分割)-非必填
	 */
	private String bizIds;
	
	/**
	 * 商品描述
	 * 苹果：商家充值余额I、商家充值积分I、缴纳保证金I、用户充值余额I、用户充值积分I、订单付款I、买单I
	 * 安卓：商家充值余额A、商家充值积分A、缴纳保证金A、用户充值余额A、用户充值积分A、订单付款A、买单A
	 */
	private String body;
	
	/**
	 * 用户编号
	 */
	private String userNum;
	
	/**
	 * 业务标记
	 */
	private ThirdPartyBizFlagEnum bizFlagEnum;
	
	
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getBizIds() {
		return bizIds;
	}
	public void setBizIds(String bizIds) {
		this.bizIds = bizIds;
	}
	public UserTypeEnum getUserTypeEnum() {
		return userTypeEnum;
	}
	public void setUserTypeEnum(UserTypeEnum userTypeEnum) {
		this.userTypeEnum = userTypeEnum;
	}
	public ThirdPartyBizFlagEnum getBizFlagEnum() {
		return bizFlagEnum;
	}
	public void setBizFlagEnum(ThirdPartyBizFlagEnum bizFlagEnum) {
		this.bizFlagEnum = bizFlagEnum;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
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