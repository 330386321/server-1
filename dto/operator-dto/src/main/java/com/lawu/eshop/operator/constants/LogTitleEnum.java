package com.lawu.eshop.operator.constants;

/**
 * @author meishuquan
 * @date 2017/5/3.
 */
public enum LogTitleEnum {

    AD_DOWN((byte) 0x01, "广告下架"),
    AD_DELETE((byte) 0x01, "广告删除"),
    AD_VIDEO_AUDIT_SUCCESS((byte) 0x01, "视频广告审核通过"),
    AD_VIDEO_AUDIT_FAIL((byte) 0x01, "视频广告审核不通过"),

    PRODUCT_DOWN((byte) 0x02, "商品下架"),
    PRODUCT_DELETE((byte) 0x02, "商品删除"),

    ORDER_PENDING((byte) 0x03, "待处理"),
    ORDER_PENDING_PAYMENT((byte) 0x03, "待付款"),
    ORDER_BE_SHIPPED((byte) 0x03, "待发货"),
    ORDER_TO_BE_RECEIVED((byte) 0x03, "待收货"),
    ORDER_TRADING_SUCCESS((byte) 0x03, "交易成功"),
    ORDER_CANCEL_TRANSACTION((byte) 0x03, "交易关闭"),
    ORDER_REFUNDING((byte) 0x03, "退款中"),
    ORDER_REFUNDING_AGREE((byte) 0x03, "退款给买家"),
    ORDER_REFUNDING_CANCEL((byte) 0x03, "买家撤销退款"),

    STORE_AUDIT_SUCCESS((byte) 0x04, "门店审核通过"),
    STORE_AUDIT_FAIL((byte) 0x04, "门店审核不通过");

    private Byte value;
    private String name;

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    LogTitleEnum(Byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public static LogTitleEnum getEnum(Byte value) {
        LogTitleEnum[] values = LogTitleEnum.values();
        for (LogTitleEnum object : values) {
            if (object.getValue().equals(value)) {
                return object;
            }
        }
        return null;
    }
}
