package com.lawu.eshop.ad.constants;

public enum TypeEnum {
	
	TYPE_LINK((byte) 0x01),      //链接
	TYPE_PRODUCT((byte) 0x02),     //商品
	TYPE_STORE((byte) 0x03);     //商品
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
