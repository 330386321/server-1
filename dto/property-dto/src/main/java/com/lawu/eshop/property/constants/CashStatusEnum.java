package com.lawu.eshop.property.constants;

/**
 * @author Sunny
 * @date 2017/3/30
 */
public enum CashStatusEnum {
	APPLY((byte) 0x01), // 申请中
	ACCEPT((byte) 0x02), // 受理中
	SUCCESS((byte) 0x03), // 成功
	FAILURE((byte) 0x04);// 失败
	public Byte val;

	CashStatusEnum(Byte val) {
		this.val = val;
	}

	public static CashStatusEnum getEnum(Byte val) {
		CashStatusEnum[] values = CashStatusEnum.values();
		for (CashStatusEnum object : values) {
			if (object.val.equals(val)) {
				return object;
			}
		}
		return null;
	}
}
