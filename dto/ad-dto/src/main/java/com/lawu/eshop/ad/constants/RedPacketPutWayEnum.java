package com.lawu.eshop.ad.constants;

public enum RedPacketPutWayEnum {
	
	PUT_WAY_COMMON((byte) 0x01),      //普通
	PUT_WAY_LUCK((byte) 0x02);     //手气
    public Byte val;

    RedPacketPutWayEnum(Byte val) {
        this.val = val;
    }

    public static RedPacketPutWayEnum getEnum(Byte val) {
    	RedPacketPutWayEnum[] values = RedPacketPutWayEnum.values();
        for (RedPacketPutWayEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }

}
