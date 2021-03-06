package com.lawu.eshop.operator.constants;

/**
 * @author meishuquan
 * @date 2017/5/3.
 */
public enum ModuleEnum {

    ALL((byte) 0x00),       //查询条件，所有
    AD((byte) 0x01),       //广告
    PRODUCT((byte) 0x02),  //商品
    ORDER((byte) 0x03),    //订单
    STORE((byte) 0x04);    //门店

    public Byte val;

    ModuleEnum(Byte val) {
        this.val = val;
    }

    public static ModuleEnum getEnum(Byte val) {
        ModuleEnum[] values = ModuleEnum.values();
        for (ModuleEnum object : values) {
            if (object.val.equals(val)) {
                return object;
            }
        }
        return null;
    }
}
