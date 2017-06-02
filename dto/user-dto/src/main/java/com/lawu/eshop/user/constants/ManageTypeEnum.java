package com.lawu.eshop.user.constants;

public enum ManageTypeEnum {
	

	COMMON((byte) 0x01),     //普通
	ENTITY((byte) 0x02);      //实体
    public Byte val;

    ManageTypeEnum(Byte val) {
        this.val = val;
    }

    public static ManageTypeEnum getEnum(Byte val) {
    	ManageTypeEnum[] values = ManageTypeEnum.values();
        for (ManageTypeEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }

}
