package com.lawu.eshop.mall.constants;

public enum ShoppingOrderStatusEnum {

	// 订单状态(0-待付款|1-待发货|2-交易成功|3-交易取消|4-待商家确认|5-待退货|6-待退款|7-退款成功)

	/**
	 * 0-待付款
	 */
	PENDING_PAYMENT((byte) 0x00),

	/**
	 * 1-待发货
	 */
	BE_SHIPPED((byte) 0x01),

	/**
	 * 2-交易成功
	 */
	TRADING_SUCCESS((byte) 0x02),

	/**
	 * 3-交易取消
	 */
	CANCEL_TRANSACTION((byte) 0x03),

	/**
	 * 4-待商家确认
	 */
	TO_BE_CONFIRMED((byte) 0x04),

	/**
	 * 5-待退货
	 */
	TO_BE_RETURNED((byte) 0x05),

	/**
	 * 6-待退款
	 */
	TO_BE_REFUNDED((byte) 0x06),

	/**
	 * 7-退款成功
	 */
	REFUND_SUCCESSFULLY((byte) 0x07);

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
