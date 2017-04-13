package com.lawu.eshop.property.constants;

/**
 * 
 * <p>
 * Description: 第三方支付状态枚举
 * </p>
 * @author Yangqh
 * @date 2017年4月12日 下午9:15:55
 *
 */
public enum ThirdPayStatusEnum {
	PAYING((byte) 0x01), // 待支付
	SUCCESS((byte) 0x02), // 成功
	FAILURE((byte) 0x03); // 失败
	public Byte val;

	ThirdPayStatusEnum(Byte val) {
		this.val = val;
	}

	public static ThirdPayStatusEnum getEnum(Byte val) {
		ThirdPayStatusEnum[] values = ThirdPayStatusEnum.values();
		for (ThirdPayStatusEnum object : values) {
			if (object.val.equals(val)) {
				return object;
			}
		}
		return null;
	}
}
