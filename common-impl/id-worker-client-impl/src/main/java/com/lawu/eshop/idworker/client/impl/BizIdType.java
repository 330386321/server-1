package com.lawu.eshop.idworker.client.impl;

/**
 * 业务ID类型
 * @author Leach
 * @date 2017/10/24
 */
public enum BizIdType {
    // 会员
    MEMBER("M"),
    // 商家
    MERCHANT("B"),
    // 订单
    ORDER("O");

    private String prefix;

    BizIdType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
