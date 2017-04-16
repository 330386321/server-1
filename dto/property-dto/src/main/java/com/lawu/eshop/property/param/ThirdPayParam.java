package com.lawu.eshop.property.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.lawu.eshop.property.constants.ThirdPartyBizFlagEnum;
import com.lawu.eshop.property.constants.ThirdPayBodyEnum;

import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: app调用支付宝前请求参数
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月6日 下午5:36:40
 *
 */
public class ThirdPayParam {

	@ApiParam(name = "bizIds", required = true, value = "业务表ID(支持多个,用英文逗号分割)")
	@NotBlank(message = "bizIds不能为空")
	private String bizIds;

	@ApiParam(name = "thirdPayBodyEnum", required = true, value = "商品描述（苹果：商家充值余额I、商家充值积分I、缴纳保证金I、用户充值余额I、用户充值积分I、订单付款I、买单I；安卓：商家充值余额A、商家充值积分A、缴纳保证金A、用户充值余额A、用户充值积分A、订单付款A、买单A；网页：PC：商家充值余额P、商家充值积分P、缴纳保证金P）")
	@NotNull(message = "thirdPayBodyEnum不能为空")
	private ThirdPayBodyEnum thirdPayBodyEnum;

	@ApiParam(name = "bizFlagEnum", required = true, value = "交易类型（商家：BUSINESS_PAY_BALANCE:商家充值余额;BUSINESS_PAY_POINT:商家充值积分;BUSINESS_PAY_BOND:缴纳保证金;用户：MEMBER_PAY_BALANCE:用户充值余额;MEMBER_PAY_POINT:用户充值积分;MEMBER_PAY_ORDER：订单付款；MEMBER_PAY_BILL:买单）")
	@NotNull(message = "bizFlagEnum不能为空")
	private ThirdPartyBizFlagEnum bizFlagEnum;

	public ThirdPayBodyEnum getThirdPayBodyEnum() {
		return thirdPayBodyEnum;
	}

	public void setThirdPayBodyEnum(ThirdPayBodyEnum thirdPayBodyEnum) {
		this.thirdPayBodyEnum = thirdPayBodyEnum;
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

}