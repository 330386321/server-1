package com.lawu.eshop.mq.dto.order.constants;

/**
 * 支付方式
 * 
 * @author Sunny
 * @date 2017年4月17日
 */
public enum TransactionPayTypeEnum {

	/**
	 * 余额
	 */
	BALANCE((byte) 0x01),

	/**
	 * 支付宝
	 */
	ALIPAY((byte) 0x02),

	/**
	 * 微信
	 */
	WX((byte) 0x03),

	/**
	 * 平台充值
	 */
	PLAT((byte) 0x04);

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
			if (object.getVal().equals(val)) {
				return object;
			}
		}
		return null;
	}
}
