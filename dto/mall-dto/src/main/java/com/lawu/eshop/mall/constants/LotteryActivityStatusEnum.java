package com.lawu.eshop.mall.constants;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
public enum LotteryActivityStatusEnum {

    PUBLISHED((byte) 0x01, "进行中"),
    UN_PUBLISH((byte) 0x02, "未发布"),
    FINISHED((byte) 0x03, "已结束"),
    CANCEL((byte) 0x04, "下架"),
    DELETE((byte) 0x05, "删除");
    private Byte val;
    private String name;

    LotteryActivityStatusEnum(Byte val, String name) {
        this.val = val;
        this.name = name;
    }

    public static LotteryActivityStatusEnum getEnum(Byte val) {
        LotteryActivityStatusEnum[] values = LotteryActivityStatusEnum.values();
        for (LotteryActivityStatusEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }

    public Byte getVal() {
        return val;
    }

    public String getName() {
        return name;
    }
}
