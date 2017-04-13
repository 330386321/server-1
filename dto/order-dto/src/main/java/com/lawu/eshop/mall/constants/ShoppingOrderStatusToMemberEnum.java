package com.lawu.eshop.mall.constants;

public enum ShoppingOrderStatusToMemberEnum {

	/**
	 * 1-待付款
	 */
	PENDING_PAYMENT((byte) 0x01),

	/**
	 * 2-待发货
	 */
	BE_SHIPPED((byte) 0x02),
	
	/**
	 * 3-待收货
	 */
	TO_BE_RECEIVED((byte) 0x03),

	/**
	 * 4-待评价(订单状态处于交易完成的状态并且未评价)
	 */
	BE_EVALUATED((byte)0x04);

	private Byte value;

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	ShoppingOrderStatusToMemberEnum(Byte value) {
		this.value = value;
	}
	
	public static ShoppingOrderStatusToMemberEnum getEnum(Byte value){
		for (ShoppingOrderStatusToMemberEnum item : ShoppingOrderStatusToMemberEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
