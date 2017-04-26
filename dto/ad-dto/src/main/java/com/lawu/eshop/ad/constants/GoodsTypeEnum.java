package com.lawu.eshop.ad.constants;

public enum GoodsTypeEnum {
	
	SHOPPING_BUY((byte) 0x01),      //E店必够
	SHOPPING_GOODS((byte) 0x02),     //特色好货
	SHOPPING_BENEFIT((byte) 0x03);     //实惠单品
    public Byte val;

    GoodsTypeEnum(Byte val) {
        this.val = val;
    }

    public static GoodsTypeEnum getEnum(Byte val) {
    	GoodsTypeEnum[] values = GoodsTypeEnum.values();
        for (GoodsTypeEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }

}
