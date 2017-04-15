package com.lawu.eshop.mall.constants;

public enum ShoppingRefundDetailStatusEnum {

	// 状态(0-无效|1-有效)
	
	/**
	 * 0-无效
	 */
	INVALID((byte)0x00),
	
	/**
	 * 1-有效
	 */
	VALID((byte)0x01);

	private Byte value;

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	ShoppingRefundDetailStatusEnum(Byte value) {
		this.value = value;
	}
	
	public static ShoppingRefundDetailStatusEnum getEnum(Byte value){
		for (ShoppingRefundDetailStatusEnum item : ShoppingRefundDetailStatusEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
