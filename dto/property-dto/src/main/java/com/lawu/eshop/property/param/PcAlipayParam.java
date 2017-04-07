package com.lawu.eshop.property.param;

import java.io.Serializable;

import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;

import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: pc调用支付宝前请求参数
 * </p>
 * @author Yangqh
 * @date 2017年4月7日 下午2:15:10
 *
 */
public class PcAlipayParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiParam (name="totalAmount",required = true, value = "金额")
	private String totalAmount;
	
	@ApiParam (name="outTradeNo",required = true, value = "商户订单号")
	private String outTradeNo;
	
	@ApiParam (name="subject",required = true, value = "交易标题")
	private String subject;

	@ApiParam (name="bizId", value = "业务表ID-非必填(缴纳保证金时必填)")
	private String bizId;

	@ApiParam (name="bizFlagEnum",required = true, value = "交易类型（BUSINESS_PAY_BALANCE:商家充值余额;BUSINESS_PAY_POINT:商家充值积分;BUSINESS_PAY_BOND:缴纳保证金;MEMBER_PAY_BALANCE:用户充值余额;MEMBER_PAY_POINT:用户充值积分;MEMBER_PAY_ORDER：订单付款；MEMBER_PAY_BILL:买单）")
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

	public ThirdPartyBizFlagEnum getBizFlagEnum() {
		return bizFlagEnum;
	}

	public void setBizFlagEnum(ThirdPartyBizFlagEnum bizFlagEnum) {
		this.bizFlagEnum = bizFlagEnum;
	}

}