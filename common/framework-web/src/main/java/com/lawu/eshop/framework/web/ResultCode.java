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

    // 公共代码
    public static final int SUCCESS = 1000;
    public static final int FAIL = 1001;
    public static final int RESOURCE_NOT_FOUND = 1002;
    public static final int ID_EMPTY = 1003;
    public static final int REQUIRED_PARM_EMPTY = 1004;
    public static final int SAVE_FAIL = 1005;
    public static final int SMS_SEND_HOUR_LIMIT = 1006;
    public static final int SMS_SEND_IP_LIMIT = 1007;
    public static final int SMS_SEND_MOBILE_LIMIT = 1008;
    public static final int VERIFY_FAIL = 1009;
    public static final int IMAGE_WRONG_UPLOAD =1010;
    public static final int IMAGE_FORMAT_WRONG_UPLOAD =1011;
    public static final int RECORD_EXIST =1012;

    // 用户模块代码 2xxx
    public static final int MEMBER_WRONG_PWD = 2000;
    public static final int USER_WRONG_ID = 2001;
    public static final int USER_WRONG_IDCARD =2002;
    public static final int IMAGE_WRONG_UPLOAD_STORE =2003;
    public static final int IMAGE_WRONG_UPLOAD_STORE_ENVIRONMENT = 2004;
    public static final int IMAGE_WRONG_UPLOAD_LICENSE = 2005;
    public static final int IMAGE_WRONG_UPLOAD_IDCARD = 2006;
    
    // 商品模块代码 3xxx
    
    // 订单模块代码 4xxx
    
    // 广告模块代码 5xxx

    // 资产模块代码 6xxx

    // 商城模块代码 7xxx
    
    
    
    // 初始化状态码与文字说明
    static {

        // 公共代码 1xxx
        ResultCode.messageMap.put(SUCCESS, "success");
        ResultCode.messageMap.put(FAIL, "fail");
        ResultCode.messageMap.put(RESOURCE_NOT_FOUND, "ID对应数据不存在");
        ResultCode.messageMap.put(ID_EMPTY, "ID不能为空");
        ResultCode.messageMap.put(REQUIRED_PARM_EMPTY, "必填参数不能为空");
        ResultCode.messageMap.put(SAVE_FAIL, "保存失败");
        //ResultCode.messageMap.put(INVALID_TOKEN, "TOKEN无效");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD, "上传图片失败");
        ResultCode.messageMap.put(IMAGE_FORMAT_WRONG_UPLOAD, "上传图片格式错误");
        ResultCode.messageMap.put(RECORD_EXIST, "记录已经存在");

        ResultCode.messageMap.put(SMS_SEND_HOUR_LIMIT, "超过发送限制（一小时内同一手机号码只能发送2次)");
        ResultCode.messageMap.put(SMS_SEND_IP_LIMIT, "超过发送限制（单个IP 24小时内只能发送5次)");
        ResultCode.messageMap.put(SMS_SEND_MOBILE_LIMIT, "超过发送限制（同一手机号24小时内只能发送5次)");
        ResultCode.messageMap.put(VERIFY_FAIL, "验证不通过");

        // 用户模块 2xxx
        ResultCode.messageMap.put(MEMBER_WRONG_PWD, "用户名或密码错误");
        ResultCode.messageMap.put(USER_WRONG_ID, "ID不存在");
        ResultCode.messageMap.put(USER_WRONG_IDCARD, "身份证号不正确");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_STORE, "请上传门店照");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_STORE_ENVIRONMENT, "请上传门店环境照");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_LICENSE, "请上传营业执照");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_IDCARD, "手持身份证照");

        // 商品模块 3xxx
        
        
        // 订单模块 4xxx


        // 广告模块 5xxx

        
        // 资产模块 6xxx

        
        // 商城模块 7xxx

        
    }

    public static String get(int code) {
        return ResultCode.messageMap.get(code);
    }

}
