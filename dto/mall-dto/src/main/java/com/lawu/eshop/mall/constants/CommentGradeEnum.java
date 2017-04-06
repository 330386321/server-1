package com.lawu.eshop.mall.constants;

/**
 * @author zhangyong
 * @date 2017/4/5.
 */
public enum CommentGradeEnum {
    COMMENT_STAR_LEVEL_ONE((byte) 0x01),//一星
    COMMENT_STAR_LEVEL_TWO((byte) 0x01),//二星
    COMMENT_STAR_LEVEL_THREE((byte) 0x01),//三星
    COMMENT_STAR_LEVEL_FOUR((byte) 0x01),//四星
    COMMENT_STAR_LEVEL_FIVE((byte) 0x01);//五星

    public Byte val;

    CommentGradeEnum(Byte val) {
        this.val = val;
    }

    public static CommentGradeEnum getEnum(Byte val) {
        CommentGradeEnum[] values = CommentGradeEnum.values();
        for (CommentGradeEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }

}
