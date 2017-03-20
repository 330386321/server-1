package com.lawu.eshop.framework.web;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leach
 * @date 2017/3/13
 */
public class ResultCode {

    private static Map<Integer, String> messageMap = new HashMap<>();

    public static final int SUCCESS_CODE = 200;
    public static final int NATIVE_BAD_REQUEST = 201;
    public static final int BAD_REQUEST = 400;
    public static final int NOT_AUTHORIZATION = 401;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int NOT_ACCEPTABLE = 406;
    public static final int INTERNAL_SERVER_ERROR = 500;

    // 初始化状态码与文字说明
    static {
        ResultCode.messageMap.put(ResultCode.SUCCESS_CODE, "success");
        ResultCode.messageMap.put(ResultCode.NATIVE_BAD_REQUEST, "内部请求异常");

        ResultCode.messageMap.put(ResultCode.BAD_REQUEST, "错误请求");
        ResultCode.messageMap.put(ResultCode.NOT_AUTHORIZATION, "无权访问");
        ResultCode.messageMap.put(ResultCode.METHOD_NOT_ALLOWED, "方法不允许访问");
        ResultCode.messageMap.put(ResultCode.NOT_ACCEPTABLE, "请求不可达");
        ResultCode.messageMap.put(ResultCode.INTERNAL_SERVER_ERROR, "服务器内部异常");
    }

    public static String get(int code) {
        return ResultCode.messageMap.get(code);
    }

    public static void putMessages(int code, String message) {
        ResultCode.messageMap.put(code, message);
    }
}
