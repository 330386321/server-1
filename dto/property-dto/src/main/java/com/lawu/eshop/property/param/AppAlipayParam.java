package com.lawu.eshop.property.param;

import java.io.Serializable;

import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;

import io.swagger.annotations.ApiParam;

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

	@ApiParam (name="totalAmount",required = true, value = "金额")
	private String totalAmount;
	
	@ApiParam (name="outTradeNo",required = true, value = "商户订单号")
	private String outTradeNo;
	
	@ApiParam (name="subject",required = true, value = "交易标题")
	private String subject;
	
	@ApiParam (name="bizIds", value = "业务表ID-非必填(订单付款时必填，订单付款时支持多个,用英文逗号分割)")
	private String bizIds;
	
	/**
	 * 商品描述
	 * 苹果：商家充值余额I、商家充值积分I、缴纳保证金I、用户充值余额I、用户充值积分I、订单付款I、买单I
	 * 安卓：商家充值余额A、商家充值积分A、缴纳保证金A、用户充值余额A、用户充值积分A、订单付款A、买单A
	 */
	@ApiParam (name="body", required = true, value = "商品描述（苹果：商家充值余额I、商家充值积分I、缴纳保证金I、用户充值余额I、用户充值积分I、订单付款I、买单I；安卓：商家充值余额A、商家充值积分A、缴纳保证金A、用户充值余额A、用户充值积分A、订单付款A、买单A）")
	private String body;
	
	@ApiParam (name="bizFlagEnum",required = true, value = "交易类型（BUSINESS_PAY_BALANCE:商家充值余额;BUSINESS_PAY_POINT:商家充值积分;BUSINESS_PAY_BOND:缴纳保证金;MEMBER_PAY_BALANCE:用户充值余额;MEMBER_PAY_POINT:用户充值积分;MEMBER_PAY_ORDER：订单付款；MEMBER_PAY_BILL:买单）")
	private ThirdPartyBizFlagEnum bizFlagEnum;
	
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