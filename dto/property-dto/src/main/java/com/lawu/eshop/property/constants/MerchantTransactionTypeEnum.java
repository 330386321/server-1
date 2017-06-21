package com.lawu.eshop.property.constants;

/**
 * 商家交易类型枚举
 * 
 * @author Sunny
 * @date 2017/3/29
 */
public enum MerchantTransactionTypeEnum {
	
	PAY((byte)0x64,"买单收入"),							//100
	ORDER((byte)0x65,"订单交易"),						//101
	LOWER_INCOME((byte)0x66,"广告提成"),					//102
	RECHARGE((byte)0x67,"余额充值"),						//103
	INTEGRAL_RECHARGE((byte)0x69,"积分充值"),			//105
	WITHDRAW((byte)0x6B,"提现"),							//107
	WITHDRAW_BACK((byte)0x6C,"提现失败"),				//108
	INVITE_FANS((byte)0x6D,"邀请粉丝"),					//109
	ADD_AD((byte)0x6E,"投放广告"),						//110
	ADD_RED_PACKET((byte)0x6F,"发红包"),					//111
	AD_RETURN_POINT((byte)0x70,"积分退还"),				//112
	DEPOSIT((byte)0x71,"缴纳保证金"),						//113-需求说不要
	DEPOSIT_REFUND((byte)0x72,"保证金退款"),				//114
	SALES_COMMISSION((byte) 0x73, "推荐E友收益"),			//115
	VOLUME_COMMISSION((byte) 0x74, "推荐商家收益"),		//116
	BACKAGE((byte) 0x75, "平台充值");						//117 后台充值
	
	
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
	
	public static MerchantTransactionTypeEnum getEnum(Byte val) {
		MerchantTransactionTypeEnum[] values = MerchantTransactionTypeEnum.values();
		for (MerchantTransactionTypeEnum object : values) {
			if (object.getValue().equals(val)) {
				return object;
			}
		}
		return null;
	}
}