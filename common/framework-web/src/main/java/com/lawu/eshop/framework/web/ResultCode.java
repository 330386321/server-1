package com.lawu.eshop.framework.web;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回码
 * @author Leach
 * @date 2017/3/13
 */
public class ResultCode {

    private static Map<Integer, String> messageMap = new HashMap<>();


    public static final int INVALID_TOKEN = 1000;
    public static final int MEMBER_WRONG_PWD = 2000;
    public static final int USER_WRONG_ID = 2100;
    public static final int PRODUCT_WRONG_ID = 3100;

    // 初始化状态码与文字说明
    static {

        // 公共代码 1xxx
        ResultCode.messageMap.put(INVALID_TOKEN, "TOKEN无效");

        // 用户模块 2xxx
        ResultCode.messageMap.put(MEMBER_WRONG_PWD, "用户名或密码错误");
        ResultCode.messageMap.put(USER_WRONG_ID, "ID不存在");

        // 商品模块 3xxx
        ResultCode.messageMap.put(PRODUCT_WRONG_ID, "ID不存在");
        
        
        // 订单模块 4xxx

        // 广告模块 5xxx

        // 资产模块 6xxx

        // 商城模块 7xxx

    }

    public static String get(int code) {
        return ResultCode.messageMap.get(code);
    }

}
