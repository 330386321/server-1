package com.lawu.eshop.framework.core.type;

/**
 * @author Leach
 * @date 2017/7/3
 */
public enum UserType {
    MEMBER((byte) 0x01), // 用户
    MERCHANT((byte) 0x02); // 商家
    public Byte val;

    UserType(Byte val) {
        this.val = val;
    }

    public static UserType getEnum(Byte val) {
        UserType[] values = UserType.values();
        for (UserType object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }
}
