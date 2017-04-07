package com.lawu.eshop.ad.constants;

public enum PutWayEnum {
	
	    PUT_WAY_AREAS((byte) 0x01),      //区域
	    PUT_WAY_FENS((byte) 0x02),     //粉丝
	    PUT_WAY_RADAR((byte) 0x03);    //雷达
	    public Byte val;

	    PutWayEnum(Byte val) {
	        this.val = val;
	    }

	    public static PutWayEnum getEnum(Byte val) {
	    	PutWayEnum[] values = PutWayEnum.values();
	        for (PutWayEnum object : values) {
	            if (object.val.equals(val)) {
	                return object;
	            }
	        }
	        return null;
	    }

}
