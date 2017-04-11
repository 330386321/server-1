package com.lawu.eshop.framework.web;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回码
 *
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
    public static final int VERIFY_PWD_FAIL = 1009;
    public static final int IMAGE_WRONG_UPLOAD = 1010;
    public static final int IMAGE_FORMAT_WRONG_UPLOAD = 1011;
    public static final int RECORD_EXIST = 1012;
    public static final int VERIFY_SMS_CODE_FAIL = 1013;
    public static final int VERIFY_PIC_CODE_FAIL = 1014;
    public static final int IMAGE_SIZE_ERROR = 1015;
    public static final int NOT_SEND_SMS_MOBILE = 1016;
    public static final int IMAGE_IS_NULL = 1017;
    public static final int MONEY_IS_POINT_2 = 1018;
    public static final int UPDATE_FAIL = 1019;

    // 用户模块代码 2xxx
    public static final int MEMBER_WRONG_PWD = 2000;
    public static final int USER_WRONG_ID = 2001;
    public static final int USER_WRONG_IDCARD = 2002;
    public static final int IMAGE_WRONG_UPLOAD_STORE = 2003;
    public static final int IMAGE_WRONG_UPLOAD_STORE_ENVIRONMENT = 2004;
    public static final int IMAGE_WRONG_UPLOAD_LICENSE = 2005;
    public static final int IMAGE_WRONG_UPLOAD_IDCARD = 2006;
    public static final int USER_POINT_NOT_ENOUGH = 2007;

    // 商品模块代码 3xxx
    public static final int IMAGE_WRONG_UPLOAD_PRODUCT_HEAD = 3000;
    public static final int IMAGE_WRONG_UPLOAD_PRODUCT_DETAIL = 3001;
    public static final int GOODS_DO_NOT_EXIST = 3002;
    public static final int GOODS_PRODUCT_FACORITE_EXIST = 3003;
    
    // 订单模块代码 4xxx
    public static final int PRODUCT_NOT_FOUND_IN_CART = 4000;
    public static final int PRODUCT_EVALUATE_TRUE = 4001;
    public static final int STATUS_NOT_CHANGED = 4002;

    // 广告模块代码 5xxx
    public static final int AD_POINT_NOT_ENOUGH = 5000;
    public static final int AD_PUT_NOT_TIME = 5001;
    public static final int AD_FACORITE_EXIST = 5002;
    

    // 资产模块代码 6xxx
    public static final int BANK_ACCOUNT_ERROR = 6000;
    public static final int CASH_MORE_NUM_MAX_MONEY_ERROR = 6001;
    public static final int PROPERTY_INFO_NULL = 6002;
    public static final int PROPERTY_INFO_OUT_INDEX = 6003;
    public static final int PROPERTY_INFO_BALANCE_LESS = 6004;
    public static final int PROPERTY_CASH_SCALE_NULL = 6005;
    public static final int PROPERTY_CASH_PAY_PWD_ERROR = 6006;
    public static final int PROPERTY_CASH_BANK_NOT_EXIST = 6007;
    public static final int PROPERTY_CASH_BANK_NOT_MATCH = 6008;
    public static final int PROPERTY_CASH_USER_INFO_NULL = 6018;
    
    public static final int ALIPAY_INIT_VALIDATOR_SUBJECT_NULL = 6009;
    public static final int ALIPAY_INIT_VALIDATOR_MONEY_NULL = 6010;
    public static final int ALIPAY_INIT_VALIDATOR_OUT_TRADE_NO_NULL = 6011;
    public static final int ALIPAY_INIT_VALIDATOR_USER_TYPE_NULL = 6012;
    public static final int ALIPAY_INIT_VALIDATOR_BODY_NULL = 6013;
    public static final int ALIPAY_INIT_VALIDATOR_USER_NUM_NULL = 6014;
    public static final int ALIPAY_INIT_VALIDATOR_BIZ_FLAG_NULL = 6015;

    public static final int WEIXIN_PAY_RETURN_CODE_FAIL = 6016;
    public static final int WEIXIN_PAY_RESULT_CODE_FAIL = 6017;
    
    public static final int CASH_BACKAGE_USER_TYPE_NULL = 6019;
    public static final int DATE_RANGE_NULL = 6020;

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
        ResultCode.messageMap.put(UPDATE_FAIL, "更新失败");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD, "上传图片失败");
        ResultCode.messageMap.put(IMAGE_FORMAT_WRONG_UPLOAD, "上传图片格式错误");
        ResultCode.messageMap.put(RECORD_EXIST, "记录已经存在");
        ResultCode.messageMap.put(IMAGE_SIZE_ERROR, "图片文件大于500K");
        ResultCode.messageMap.put(NOT_SEND_SMS_MOBILE, "与发送短信手机号不匹配");
        ResultCode.messageMap.put(IMAGE_IS_NULL, "图片格式不正确");
        ResultCode.messageMap.put(MONEY_IS_POINT_2, "金额请保留两位小数");

        ResultCode.messageMap.put(SMS_SEND_HOUR_LIMIT, "超过发送限制（一小时内同一手机号码只能发送2次)");
        ResultCode.messageMap.put(SMS_SEND_IP_LIMIT, "超过发送限制（单个IP 24小时内只能发送5次)");
        ResultCode.messageMap.put(SMS_SEND_MOBILE_LIMIT, "超过发送限制（同一手机号24小时内只能发送5次)");
        ResultCode.messageMap.put(VERIFY_PWD_FAIL, "原始密码错误");
        ResultCode.messageMap.put(VERIFY_SMS_CODE_FAIL, "短信验证码错误");
        ResultCode.messageMap.put(VERIFY_PIC_CODE_FAIL, "图片验证码错误");

        // 用户模块 2xxx
        ResultCode.messageMap.put(MEMBER_WRONG_PWD, "用户名或密码错误");
        ResultCode.messageMap.put(USER_WRONG_ID, "ID不存在");
        ResultCode.messageMap.put(USER_WRONG_IDCARD, "身份证号不正确");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_STORE, "请上传门店照");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_STORE_ENVIRONMENT, "请上传门店环境照");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_LICENSE, "请上传营业执照");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_IDCARD, "手持身份证照");
        ResultCode.messageMap.put(USER_POINT_NOT_ENOUGH, "用户积分不足");

        // 商品模块 3xxx
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_PRODUCT_HEAD, "请上传商品滚动图片");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_PRODUCT_DETAIL, "请上传商品详情图片");
        ResultCode.messageMap.put(GOODS_DO_NOT_EXIST, "商品型号对应的商品信息不存在");
        ResultCode.messageMap.put(GOODS_PRODUCT_FACORITE_EXIST, "商品已经被收藏");
        
        // 订单模块 4xxx
        ResultCode.messageMap.put(PRODUCT_NOT_FOUND_IN_CART, "商品不在购物车内");
        ResultCode.messageMap.put(PRODUCT_EVALUATE_TRUE, "订单已经评价过");
        ResultCode.messageMap.put(STATUS_NOT_CHANGED, "订单状态不允许被更改");

        // 广告模块 5xxx
        ResultCode.messageMap.put(AD_POINT_NOT_ENOUGH, "当前积分不够");
        ResultCode.messageMap.put(AD_PUT_NOT_TIME, "投放时间没有超过两个星期");
        ResultCode.messageMap.put(AD_FACORITE_EXIST, "广告已经被收藏");

        // 资产模块 6xxx
        ResultCode.messageMap.put(BANK_ACCOUNT_ERROR, "请重新输入你的银行卡号");
        ResultCode.messageMap.put(CASH_MORE_NUM_MAX_MONEY_ERROR, "自然月提现次数大于1次时，提现金额必须大于5元");
        ResultCode.messageMap.put(PROPERTY_INFO_NULL, "用户对应财产记录为空");
        ResultCode.messageMap.put(PROPERTY_INFO_OUT_INDEX, "用户对应财产记录错误，存在大于1条记录");
        ResultCode.messageMap.put(PROPERTY_INFO_BALANCE_LESS, "提现余额不足");
        ResultCode.messageMap.put(PROPERTY_CASH_SCALE_NULL, "提现比例系统参数未配置");
        ResultCode.messageMap.put(PROPERTY_CASH_PAY_PWD_ERROR, "支付密码错误");
        ResultCode.messageMap.put(PROPERTY_CASH_BANK_NOT_EXIST, "提交的银行卡ID不存在");
        ResultCode.messageMap.put(PROPERTY_CASH_BANK_NOT_MATCH, "提交的银行卡与用户不匹配");
        ResultCode.messageMap.put(PROPERTY_CASH_USER_INFO_NULL, "提交查询用户冗余信息为空");
        
        ResultCode.messageMap.put(ALIPAY_INIT_VALIDATOR_SUBJECT_NULL, "app支付时subject参数不能为空");
        ResultCode.messageMap.put(ALIPAY_INIT_VALIDATOR_MONEY_NULL, "app支付时total_amount参数不能为空");
        ResultCode.messageMap.put(ALIPAY_INIT_VALIDATOR_OUT_TRADE_NO_NULL, "app支付时out_trade_no参数不能为空");
        ResultCode.messageMap.put(ALIPAY_INIT_VALIDATOR_USER_TYPE_NULL, "app支付时userTypeEnum参数不能为空");
        ResultCode.messageMap.put(ALIPAY_INIT_VALIDATOR_BODY_NULL, "app支付时body参数不能为空");
        ResultCode.messageMap.put(ALIPAY_INIT_VALIDATOR_USER_NUM_NULL, "app支付时userNum参数不能为空");
        ResultCode.messageMap.put(ALIPAY_INIT_VALIDATOR_BIZ_FLAG_NULL, "app支付时bizFlagEnum参数不能为空");
        
        ResultCode.messageMap.put(WEIXIN_PAY_RETURN_CODE_FAIL, "微信支付时预支付接口return_code返回FAIL");
        ResultCode.messageMap.put(WEIXIN_PAY_RESULT_CODE_FAIL, "微信支付时预支付接口result_code返回FAIL");
        
        ResultCode.messageMap.put(CASH_BACKAGE_USER_TYPE_NULL, "用户类型不能为空");
        ResultCode.messageMap.put(DATE_RANGE_NULL, "时间区间不能为空");


        // 商城模块 7xxx


    }

    public static String get(int code) {
        return ResultCode.messageMap.get(code);
    }

}
