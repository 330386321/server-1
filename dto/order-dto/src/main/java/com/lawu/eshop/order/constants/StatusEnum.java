package com.lawu.eshop.order.constants;

public enum StatusEnum {

	// 状态(0-无效|1-有效)

	/**
	 * 0-无效
	 */
	INVALID((byte) 0x00),

	/**
	 * 1-有效
	 */
	VALID((byte) 0x01);

	private Byte value;

	StatusEnum(Byte value) {
		this.value = value;
	}

	public Byte getValue() {
		return value;
	}

	public static StatusEnum getEnum(Byte value) {
		for (StatusEnum item : StatusEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
