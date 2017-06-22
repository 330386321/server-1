package com.lawu.eshop.user.constants;

/**
 * 
 * @author Sunny
 * @date 2017年4月20日
 */
public enum StatusEnum {

	/**
	 * 无效
	 */
    NO_VALID((byte) 0x00),
    
    /**
     * 有效
     */
    VALID((byte) 0x01);
	
    private Byte value;
    
    StatusEnum(Byte value) {
        this.value = value;
    }
    
    public Byte getValue() {
		return value;
	}

    public static StatusEnum getEnum(Byte value) {
        StatusEnum[] values = StatusEnum.values();
        for (StatusEnum object : values) {
            if (object.getValue().equals(value)) {
                return object;
            }
        }
        return null;
    }
}
