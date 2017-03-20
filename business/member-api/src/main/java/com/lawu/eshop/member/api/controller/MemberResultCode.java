package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.framework.web.ResultCode;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/3/14
 */
@Component
public class MemberResultCode {

    public static final int INVALID_TOKEN = 1000;
    public static final int MEMBER_WRONG_PWD = 2000;

    // 初始化状态码与文字说明
    static {
        // 公共代码 1xxx
        ResultCode.putMessages(INVALID_TOKEN, "TOKEN无效");

        // 用户模块 2xxx
        ResultCode.putMessages(MEMBER_WRONG_PWD, "用户名或密码错误");

        // 商家模块 3xxx

        // 商品模块 4xxx

        // 订单模块 5xxx

        // 资产模块 6xxx

        // 广告模块 7xxx

        // 商城模块 8xxx

        // 统计模块 9xxx
    }

}
