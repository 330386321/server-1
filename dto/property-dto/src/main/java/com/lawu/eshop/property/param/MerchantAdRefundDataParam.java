package com.lawu.eshop.property.param;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 商家 E咻 & 红包 下架后退款原路退
 */
public class MerchantAdRefundDataParam {

	private String adId;

	private String refundMoney;

	private String userNum;

	private TransactionPayTypeEnum transactionPayTypeEnum;

	private String tradeNo;

	public String getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}

	public TransactionPayTypeEnum getTransactionPayTypeEnum() {
		return transactionPayTypeEnum;
	}

	public void setTransactionPayTypeEnum(TransactionPayTypeEnum transactionPayTypeEnum) {
		this.transactionPayTypeEnum = transactionPayTypeEnum;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getAdId() {

		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
}
