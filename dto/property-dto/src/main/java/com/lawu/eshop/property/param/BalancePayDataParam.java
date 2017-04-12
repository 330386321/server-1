package com.lawu.eshop.property.param;

import org.hibernate.validator.constraints.NotBlank;

import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;

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

	// 用户账号
	@NotBlank(message = "account不能为空")
	private String account;
	
	private MemberTransactionTypeEnum memberTransactionTypeEnum;
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

}