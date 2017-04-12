package com.lawu.eshop.property.param;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;

/**
 * 
 * <p>
 * Description: 业务操作需要处理财产积分
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月11日 下午5:35:25
 *
 */
public class PropertyInfoDataParam {

	// 用户编号-api传入
	@NotBlank(message = "userNum不能为空")
	private String userNum;
	
	//积分
	@NotBlank(message = "point不能为空")
	@Pattern(regexp = "^\\d{0,8}\\.{0,1}(\\d{1,2})?$", message = "point格式错误或小数位不超过2位")
	private String point;
	
	@NotNull(message="transactionTitleEnum不能为空")
	private TransactionTitleEnum transactionTitleEnum;
	
	//业务类型：根据业务需要传，如果是会员业务传memberTransactionTypeEnum
	private MemberTransactionTypeEnum memberTransactionTypeEnum;
	
	//业务类型：根据业务需要传，如果是商家业务传merchantTransactionTypeEnum
	private MerchantTransactionTypeEnum merchantTransactionTypeEnum;

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public MemberTransactionTypeEnum getMemberTransactionTypeEnum() {
		return memberTransactionTypeEnum;
	}

	public void setMemberTransactionTypeEnum(MemberTransactionTypeEnum memberTransactionTypeEnum) {
		this.memberTransactionTypeEnum = memberTransactionTypeEnum;
	}

	public MerchantTransactionTypeEnum getMerchantTransactionTypeEnum() {
		return merchantTransactionTypeEnum;
	}

	public void setMerchantTransactionTypeEnum(MerchantTransactionTypeEnum merchantTransactionTypeEnum) {
		this.merchantTransactionTypeEnum = merchantTransactionTypeEnum;
	}

	public TransactionTitleEnum getTransactionTitleEnum() {
		return transactionTitleEnum;
	}

	public void setTransactionTitleEnum(TransactionTitleEnum transactionTitleEnum) {
		this.transactionTitleEnum = transactionTitleEnum;
	}

}