package com.lawu.eshop.property.param;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;

/**
 * 
 * <p>
 * Description: 余额支付参数对象
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月11日 下午5:35:25
 *
 */
public class BalancePayDataParam extends BalancePayParam{

	// 用户编号
	@NotBlank(message = "userNum不能为空")
	private String userNum;
	
	private String sideUserNum;

	// 用户账号
	@NotBlank(message = "account不能为空")
	private String account;
	
	@NotBlank(message = "totalAmount不能为空")
	@Pattern(regexp = "^\\d{0,8}\\.{0,1}(\\d{1,2})?$", message = "totalAmount格式错误要求数字或小数位不超过2位")
	private String totalAmount;
	
	//订单号
	@NotBlank(message = "orderNum不能为空")
	private String orderNum;
	
	private MemberTransactionTypeEnum memberTransactionTypeEnum;
	private MerchantTransactionTypeEnum merchantTransactionTypeEnum;
	private String title;

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public MemberTransactionTypeEnum getMemberTransactionTypeEnum() {
		return memberTransactionTypeEnum;
	}

	public void setMemberTransactionTypeEnum(MemberTransactionTypeEnum memberTransactionTypeEnum) {
		this.memberTransactionTypeEnum = memberTransactionTypeEnum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MerchantTransactionTypeEnum getMerchantTransactionTypeEnum() {
		return merchantTransactionTypeEnum;
	}

	public void setMerchantTransactionTypeEnum(MerchantTransactionTypeEnum merchantTransactionTypeEnum) {
		this.merchantTransactionTypeEnum = merchantTransactionTypeEnum;
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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	
}