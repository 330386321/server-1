package com.lawu.eshop.mall.constants;

public enum ShoppingRefundTypeEnum {

	// 退款类型(0-退款|1-退货退款)
	
	/**
	 * 0-退款
	 */
	REFUND((byte)0x00),
	
	/**
	 * 1-退货退款
	 */
	RETURN_REFUND((byte)0x01);

	private Byte value;

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	ShoppingRefundTypeEnum(Byte value) {
		this.value = value;
	}
	
	public static ShoppingRefundTypeEnum getEnum(Byte value){
		for (ShoppingRefundTypeEnum item : ShoppingRefundTypeEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
