package com.lawu.eshop.mall.constants;

/**
 * @author zhangyong
 * @date 2017/4/5.
 */
public enum CommentAnonymousEnum {
    COMMENT_ANONYMOUS_SUCCESS((byte) 0x01),//匿名
    COMMENT_ANONYMOUS_FAILED((byte) 0x00);//不匿名

    public Byte val;

    CommentAnonymousEnum(Byte val) {
        this.val = val;
    }

    public static CommentAnonymousEnum getEnum(Byte val) {
        CommentAnonymousEnum[] values = CommentAnonymousEnum.values();
        for (CommentAnonymousEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }
}
