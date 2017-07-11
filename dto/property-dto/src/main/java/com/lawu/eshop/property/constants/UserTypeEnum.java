package com.lawu.eshop.property.constants;

/**
 * 
 * <p>
 * Description: 用户类型枚举类
 * </p>
 * @author Yangqh
 * @date 2017年4月5日 下午4:17:25
 *
 */
public enum UserTypeEnum {

    MEMBER((byte) 0x01),      //用户
    MEMCHANT((byte) 0x02),     //商家
    MEMCHANT_PC((byte) 0x03);	//商家PC
    private Byte val;

    UserTypeEnum(Byte val) {
        this.val = val;
    }

    public static UserTypeEnum getEnum(Byte val) {
        UserTypeEnum[] values = UserTypeEnum.values();
        for (UserTypeEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }

	public Byte getVal() {
		return val;
	}
    
}
