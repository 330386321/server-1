package com.lawu.eshop.ad.constants;

public enum OrderTypeEnum {
	
	AD_TORLEPOINT_DESC((byte) 0x01),     //总价倒序
	AD_POINT_DESC((byte) 0x02);     //单个倒序
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
