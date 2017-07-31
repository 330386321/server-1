package com.lawu.eshop.property.param;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

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
	@NotNull(message = "userTypeEnum不能为空")
	private UserTypeEnum userTypeEnum;
	
	/**
	 * 用户编号
	 */
	@NotBlank(message = "userNum不能为空")
	private String userNum;
	
	/**
	 * 商户订单号
	 */
	@NotBlank(message = "outTradeNo不能为空")
	private String outTradeNo;
	
	/**
	 * 主题
	 */
	@NotBlank(message = "subject不能为空")
	private String subject;
	
	//对方用户编号
	private String sideUserNum;
	
	@NotBlank(message = "totalAmount不能为空")
	@Pattern(regexp = "^\\d{1,9}(\\.\\d{1,2})?$", message = "金额错误(要求最大8位整数且保留2位小数)")
	@Max(value=10000000,message="金额错误(要求不能超过10000000)")
	private String totalAmount;
	
	//商家缴纳保证金时需要回调发送消息改门店状态（事务）
	private Long merchantId;
	
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
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
	public String getSideUserNum() {
		return sideUserNum;
	}
	public void setSideUserNum(String sideUserNum) {
		this.sideUserNum = sideUserNum;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
}