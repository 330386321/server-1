package com.lawu.eshop.property.param;

import java.io.Serializable;

import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;

/**
 * 
 * <p>
 * Description: pc调用支付宝前请求参数
 * </p>
 * @author Yangqh
 * @date 2017年4月7日 下午2:15:10
 *
 */
public class PcAlipayDataParam implements Serializable {

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
	 * 业务表ID()-非必填
	 */
	private String bizId;

	/**
	 * 用户编号
	 */
	private String userNum;

	/**
	 * 业务标记：商家充值余额、商家充值积分、缴纳保证金
	 */
	private ThirdPartyBizFlagEnum bizFlagEnum;

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

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public ThirdPartyBizFlagEnum getBizFlagEnum() {
		return bizFlagEnum;
	}

	public void setBizFlagEnum(ThirdPartyBizFlagEnum bizFlagEnum) {
		this.bizFlagEnum = bizFlagEnum;
	}

}