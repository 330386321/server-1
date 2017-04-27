package com.lawu.eshop.property.constants;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月5日 下午5:29:15
 *
 */
public enum TransactionPayTypeEnum {
	BALANCE((byte) 0x01,"余额"), // 余额
	ALIPAY((byte) 0x02,"支付宝"), // 支付宝
	WX((byte) 0x03,"微信");// 微信
	public Byte val;
	public String name;

	TransactionPayTypeEnum(Byte val,String name) {
		this.val = val;
		this.name = name;
	}

	public static TransactionPayTypeEnum getEnum(Byte val) {
		TransactionPayTypeEnum[] values = TransactionPayTypeEnum.values();
		for (TransactionPayTypeEnum object : values) {
			if (object.val.equals(val)) {
				return object;
			}
		}
		return null;
	}
}
