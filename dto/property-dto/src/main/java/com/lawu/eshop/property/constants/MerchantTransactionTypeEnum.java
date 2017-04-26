package com.lawu.eshop.property.constants;

/**
 * 商家交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MerchantTransactionTypeEnum {
	
	PAY((byte)0x64,"买单"),					//100
	ORDER((byte)0x65,"订单付款"),					//101
	LOWER_INCOME((byte)0x66,"广告提成"),	//102
	RECHARGE((byte)0x67,"余额充值"),			//103
	PUT_ON((byte)0x68,"投放"),				//104
	INTEGRAL_RECHARGE((byte)0x69,"积分充值"),	//105
	REFUNDS((byte)0x6A,"退款"),				//106
	WITHDRAW((byte)0x6B,"提现"),				//107
	WITHDRAW_BACK((byte)0x6C,"提现退回"),		//108
	INVITE_FANS((byte)0x6D,"邀请粉丝"),		//109
	ADD_AD((byte)0x6E,"发广告"),				//110
	ADD_RED_PACKET((byte)0x6F,"发红包"),		//111
	AD_RETURN_POINT((byte)0x70,"退还积分"),	//112
	DEPOSIT((byte)0x71,"缴纳保证金"),			//113
	DEPOSIT_REFUND((byte)0x72,"退缴纳保证金"),	//114
	SALES_COMMISSION((byte) 0x73, "销售提成"),//115
	VOLUME_COMMISSION((byte) 0x74, "营业额提成");//116
	
	
	private Byte value;
	private String name;

	MerchantTransactionTypeEnum(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}