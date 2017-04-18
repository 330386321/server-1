package com.lawu.eshop.property.constants;


/**
 * 
 * <p>
 * Description: 订单退款时用来标记是否需要处理冻结资金
 * </p>
 * @author Yangqh
 * @date 2017年4月18日 下午3:52:33
 *
 */
public enum OrderRefundStatusEnum {
	FINISH((byte)0x01),		//订单完成
	IN_PROCESSING((byte)0x02);		//处理中未确认收货
	
	public Byte val;
	OrderRefundStatusEnum(Byte val){
		this.val = val;
	}
	
	public static OrderRefundStatusEnum getEnum(Byte val){
		OrderRefundStatusEnum[] values = OrderRefundStatusEnum.values();
		for (OrderRefundStatusEnum object : values) {
			if (object.val.equals(val)) {
				return object;
			}
		}
		return null;
	}
}
