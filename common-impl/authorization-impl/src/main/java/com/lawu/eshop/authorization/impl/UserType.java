package com.lawu.eshop.authorization.impl;

/**
 * @author Leach
 * @date 2017/10/11
 */
public enum UserType {
    MEMBER(1), // 会员
    MERCHANT(2), // 商家
    AGENT(3); // 代理商

    private int val;

    UserType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
