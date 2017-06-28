package com.lawu.eshop.order.constants;

public enum ShoppingOrderItemRefundStatusEnum {

	// 退款状态(0-待商家确认|1-填写退货地址|2-待退货|3-待退款|4-退款成功|5-退款失败|6-平台介入)

	/**
	 * 0-待商家确认
	 */
	TO_BE_CONFIRMED((byte) 0x00),

	/**
	 * 1-填写退货地址
	 */
	FILL_RETURN_ADDRESS((byte) 0x01),

	/**
	 * 2-待退货
	 */
	TO_BE_RETURNED((byte) 0x02),

	/**
	 * 3-待退款
	 */
	TO_BE_REFUNDED((byte) 0x03),

	/**
	 * 4-退款成功
	 */
	REFUND_SUCCESSFULLY((byte) 0x04),

	/**
	 * 5-退款失败
	 */
	REFUND_FAILED((byte) 0x05),

	/**
	 * 6-平台介入
	 */
	PLATFORM_INTERVENTION((byte) 0x06);

	private Byte value;

	ShoppingOrderItemRefundStatusEnum(Byte value) {
		this.value = value;
	}

	public Byte getValue() {
		return value;
	}

	public static ShoppingOrderItemRefundStatusEnum getEnum(Byte value) {
		for (ShoppingOrderItemRefundStatusEnum item : ShoppingOrderItemRefundStatusEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
