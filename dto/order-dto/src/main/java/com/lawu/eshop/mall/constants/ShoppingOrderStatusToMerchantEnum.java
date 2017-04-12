package com.lawu.eshop.mall.constants;

public enum ShoppingOrderStatusToMerchantEnum {
	
	/**
	 * 0-待付款|1-待发货|2-待收货|5-退款中
	 * 进行中
	 */
	PROCESSING((byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x05),
	
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
	 * 5-退款中
	 */
	REFUNDING((byte) 0x05),
	
	/**
	 * 4-已完成
	 */
	COMPLETED((byte) 0x03),
	
	/**
	 * 4-已关闭
	 */
	CLOSED((byte) 0x04);
	
	private Byte[] value;

	public Byte[] getValue() {
		return value;
	}

	public void setValue(Byte[] value) {
		this.value = value;
	}

	ShoppingOrderStatusToMerchantEnum(Byte...value) {
		this.value = value;
	}
	
	public static ShoppingOrderStatusToMerchantEnum getEnum(Byte value){
		for (ShoppingOrderStatusToMerchantEnum item : ShoppingOrderStatusToMerchantEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
