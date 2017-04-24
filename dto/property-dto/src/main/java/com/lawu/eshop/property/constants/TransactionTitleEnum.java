package com.lawu.eshop.property.constants;

public enum TransactionTitleEnum {

	CASH("提现"),
	CASH_FAIL_BACK("提现失败退回"),
	ORDER_PAY("订单付款"),
	ORDER_PAY_REFUND("订单退款"),
	PAY("买单"),
	INTEGRAL_RECHARGE("积分充值"),
	INVITE_FANS("邀请粉丝"),
	ADD_AD("发广告"),
	ADD_RED_PACKET("发红包"),
	AD_RETURN_POINT("广告退还"),
	USER_GET_REDPACKET("领红包"),
	CLICK_AD("点广告"),
	AD_COMMISSION("广告提成"),
	RECHARGE("余额充值"),
	DEPOSIT("缴纳保证金"),
	DEPOSIT_REFUND("缴纳保证金退款");
	
	public String val;

	TransactionTitleEnum(String val) {
		this.val = val;
	}

	public static TransactionTitleEnum getEnum(String val) {
		TransactionTitleEnum[] values = TransactionTitleEnum.values();
		for (TransactionTitleEnum object : values) {
			if (object.val.equals(val)) {
				return object;
			}
		}
		return null;
	}
}
