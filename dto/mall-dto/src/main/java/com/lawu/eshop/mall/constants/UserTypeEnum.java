package com.lawu.eshop.mall.constants;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
public enum UserTypeEnum {
    MEMBER((byte) 0x01),      //用户
    MEMCHANT((byte) 0x02);     //商家
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
