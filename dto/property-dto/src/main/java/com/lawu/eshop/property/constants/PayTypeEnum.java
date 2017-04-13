package com.lawu.eshop.property.constants;

/**
 * 
 * <p>
 * Description: 充值类型
 * </p>
 * @author Yangqh
 * @date 2017年4月12日 下午8:45:23
 *
 */
public enum PayTypeEnum {
	BALANCE((byte) 0x01), // 余额
	POINT((byte) 0x02); // 积分
	public Byte val;

	PayTypeEnum(Byte val) {
		this.val = val;
	}

	public static PayTypeEnum getEnum(Byte val) {
		PayTypeEnum[] values = PayTypeEnum.values();
		for (PayTypeEnum object : values) {
			if (object.val.equals(val)) {
				return object;
			}
		}
		return null;
	}
}
