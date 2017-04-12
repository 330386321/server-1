package com.lawu.eshop.mall.constants;

public enum ShoppingOrderStatusEnum {

	// 订单状态(0-待付款|1-待发货|2-待收货|3-交易成功|4-交易关闭|5-退款中)

	/**
	 * 0-待付款
	 */
	PENDING_PAYMENT((byte) 0x00),

	/**
	 * 1-待发货
	 */
	BE_SHIPPED((byte) 0x01),
	
	/**
	 * 2-待收货
	 */
	TO_BE_RECEIVED((byte) 0x02),

	/**
	 * 3-交易成功
	 */
	TRADING_SUCCESS((byte) 0x03),

	/**
	 * 4-交易关闭
	 */
	CANCEL_TRANSACTION((byte) 0x04),

	/**
	 * 5-退款中
	 */
	REFUNDING((byte) 0x05);

	private Byte value;

	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	ShoppingOrderStatusEnum(Byte value) {
		this.value = value;
	}
	
	public static ShoppingOrderStatusEnum getEnum(Byte value){
		for (ShoppingOrderStatusEnum item : ShoppingOrderStatusEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
