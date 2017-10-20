package com.lawu.eshop.property.constants;

public enum ManageTypeEnum {
	
	COMMON((byte) 0x01, "普通"), ENTITY((byte) 0x02, "实体");
    
    private Byte val;
    
    private String label;
    
    ManageTypeEnum(Byte val, String label) {
        this.val = val;
        this.label = label;
    }
    
    public Byte getVal() {
        return val;
    }

    public String getLabel() {
        return label;
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
