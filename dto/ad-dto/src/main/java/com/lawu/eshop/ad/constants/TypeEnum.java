package com.lawu.eshop.ad.constants;

public enum TypeEnum {
	
	TYPE_LINK((byte) 0x01),      //平面
	TYPE_PRODUCT((byte) 0x02);     //视频
    public Byte val;

    TypeEnum(Byte val) {
        this.val = val;
    }

    public static TypeEnum getEnum(Byte val) {
    	TypeEnum[] values = TypeEnum.values();
        for (TypeEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }

}
