package com.lawu.eshop.ad.constants;

public enum OrderTypeEnum {
	
	AD_TIME_DESC((byte) 0x01),      //时间倒序
	AD_TORLEPOINT_DESC((byte) 0x02),     //总价倒序
	AD_POINT_DESC((byte) 0x03);     //单个倒序
    public Byte val;

    OrderTypeEnum(Byte val) {
        this.val = val;
    }

    public static OrderTypeEnum getEnum(Byte val) {
    	OrderTypeEnum[] values = OrderTypeEnum.values();
        for (OrderTypeEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }

}
