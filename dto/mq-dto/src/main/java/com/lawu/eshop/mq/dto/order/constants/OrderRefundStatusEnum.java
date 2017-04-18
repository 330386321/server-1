package com.lawu.eshop.mq.dto.order.constants;

/**
 * 
 * @author Sunny
 * @date 2017年4月18日
 */
public enum OrderRefundStatusEnum {
	
	/**
	 * 订单完成
	 */
	FINISH((byte)0x01),
	
	/**
	 * 处理中未确认收货
	 */
	IN_PROCESSING((byte)0x02);
	
	private Byte value;
	
	public Byte getValue() {
		return value;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	OrderRefundStatusEnum(Byte value){
		this.value = value;
	}
	
	public static OrderRefundStatusEnum getEnum(Byte value){
		for (OrderRefundStatusEnum item : OrderRefundStatusEnum.values()) {
			if (item.getValue().equals(value)) {
				return item;
			}
		}
		return null;
	}
}
