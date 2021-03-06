package com.lawu.eshop.order.constants;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
public enum TransactionPayTypeEnum {

	BALANCE((byte) 0x01), // 余额

	ALIPAY((byte) 0x02), // 支付宝

	WX((byte) 0x03),// 微信

	PLAT((byte) 0x04); // 平台

	private Byte val;

	TransactionPayTypeEnum(Byte val) {
		this.val = val;
	}

	public Byte getVal() {
		return val;
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
