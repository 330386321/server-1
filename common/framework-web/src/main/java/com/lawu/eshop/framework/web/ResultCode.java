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
    public static final int NOT_FOUND_DATA = 1100;
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
    public static final int UPLOAD_VEDIO_FAIL = 1020;
    public static final int UPLOAD_SIZE_BIGER = 1021;
    public static final int PAY_PWD_NULL = 1022;
    public static final int PAY_PWD_ERROR = 1023;
    public static final int ILLEGAL_OPERATION = 1024;
    public static final int VERIFY_SMS_CODE_OVERTIME = 1025;
    public static final int INVITER_NO_EXIST = 1026;
    public static final int ACCOUNT_EXIST = 1027;
    public static final int SMS_SEND_FAIL = 1028;

    public static final int ID_CARD_RECORD_EXIST = 1029;
    public static final int REG_NUMBER_RECORD_EXIST = 1030;
    
    //FastDFS上传图片异常
    public static final int FD_FILE_ERROR =1031;
    public static final int FD_FILE_IMG_BIG =1032;
    public static final int FD_FILE_CUT_ERROR=1034;
    
    //邀请粉丝异常
    public static final int INVITE_FANS_NOT_EXIST=1035;

    // 用户模块代码 2xxx
    public static final int MEMBER_WRONG_PWD = 2000;
    public static final int USER_WRONG_ID = 2001;
    public static final int USER_WRONG_IDCARD = 2002;
    public static final int IMAGE_WRONG_UPLOAD_STORE = 2003;
    public static final int IMAGE_WRONG_UPLOAD_STORE_ENVIRONMENT = 2004;
    public static final int IMAGE_WRONG_UPLOAD_LICENSE = 2005;
    public static final int IMAGE_WRONG_UPLOAD_IDCARD = 2006;
    public static final int USER_POINT_NOT_ENOUGH = 2007;
    public static final int MERCHANT_STORE_AUDIT_EXIST = 2008;
    public static final int MERCHANT_STORE_NO_EXIST = 2009;
    public static final int MEMBER_NO_EXIST = 2010;
    public static final int BANK_CASH_EXIST = 2011;
    public static final int FANS_MERCHANT = 2012;
    public static final int MOBILE_IS_NOT_EXIST = 2013;
    public static final int MERCHANT_STORE_IS_FAVORITE = 2014;
    public static final int ACCOUNT_IS_FREEZE = 2015;
    public static final int MAX_USERREDPACKET_COUNT=2016;
    public static final int MAX_USERREDPACKET_MONTY=2017;
    public static final int MIN_USERREDPACKET_MONTY=2018;


    // 商品模块代码 3xxx
    public static final int IMAGE_WRONG_UPLOAD_PRODUCT_HEAD = 3000;
    public static final int IMAGE_WRONG_UPLOAD_PRODUCT_DETAIL = 3001;
    public static final int GOODS_PRODUCT_FACORITE_EXIST = 3003;
    public static final int GOODS_PRODUCT_INVENTORY = 3004;
    public static final int GOODS_PRODUCT_EXIST_ADFLAT = 3005;

    // 订单模块代码 4xxx
    public static final int PRODUCT_EVALUATE_TRUE = 4001;
    public static final int ORDER_NOT_CANCELED = 4002;
    public static final int ORDER_NOT_DELETE = 4003;
    public static final int ORDER_NOT_PENDING_PAYMENT = 4004;
    public static final int ORDER_NOT_RECEIVED = 4005;
    public static final int ORDER_NOT_REFUND = 4006;
    public static final int EXCEEDS_RETURN_TIME = 4007;
    public static final int NOT_SHIPPING_STATUS = 4008;
    public static final int NOT_RETURNED_STATE = 4009;
    public static final int NOT_AGREE_TO_APPLY = 4010;
    public static final int NOT_REFUNDING = 4011;
    public static final int ORDER_NOT_FILL_RETURN_ADDRESS = 4012;
    public static final int ORDER_NOT_TO_BE_REFUNDED = 4013;
    public static final int ORDER_NOT_REFUND_FAILED = 4014;
    public static final int ORDER_NOT_COMPLETE_STATUS = 4015;
    public static final int PAY_ORDER_FAVORED_AMOUNT_UNEQUAL = 4016;
    public static final int ORDER_HAS_BEEN_REFUNDED = 4017;
    public static final int INVENTORY_SHORTAGE = 4018;
    public static final int PRODUCT_HAS_EXPIRED = 4019;
    public static final int THE_ORDER_IS_BEING_PROCESSED = 4020;
    public static final int ORDER_CREATION_FAILED = 4021;
    public static final int MAX_SHOPPING_CART_QUANTITY = 4022;
    public static final int CAN_NOT_FILL_OUT_THE_RETURN_LOGISTICS = 4023;
    public static final int CAN_NOT_APPLY_FOR_PLATFORM_INTERVENTION = 4024;
    public static final int CAN_NOT_CANCEL_APPLICATION = 4025;
    public static final int CAN_NOT_FILL_IN_SHIPPING_LOGISTICS = 4026;
    public static final int CAN_NOT_AGREE_TO_APPLY = 4027;
    public static final int CAN_NOT_AGREE_TO_A_REFUND = 4028;
    public static final int CAN_NOT_FILL_IN_THE_RETURN_ADDRESS = 4029;

    // 广告模块代码 5xxx
    public static final int AD_POINT_NOT_ENOUGH = 5000;
    public static final int AD_PUT_NOT_TIME = 5001;
    public static final int AD_FACORITE_EXIST = 5002;
    public static final int AD_RED_PACKGE_EXIST = 5003;
    public static final int AD_RED_PACKGE_GET = 5004;
    public static final int AD_PRAISE_PUTED = 5005;
    public static final int AD_CLICK_EXIST = 5006;
    public static final int AD_PRAISE_POINT_GET = 5007;
    public static final int AD_CLICK_PUTED = 5008;
    public static final int AD_RED_PACKGE_PUTED=5009;
    public static final int AD_BEGIN_TIME_NOT_EXIST=5010;


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
    public static final int PROPERTY_CASH_USER_INFO_NULL = 6009;
    public static final int PROPERTY_INFO_POINT_LESS = 6010;
    public static final int BIZ_TYPE_NULL = 6011;

    public static final int CASH_BACKAGE_FAILURE_REASON_NULL = 6012;
    public static final int FREEZE_NULL = 6013;
//    public static final int FREEZE_ROWS_OUT = 6014;
    public static final int FREEZE_MONEY_LESS_REFUND_MONEY = 6015;
    public static final int DEPOSIT_IN_SYSTEM_DAYS = 6016;
    public static final int DEPOSIT_EXIST_ING_ORDER = 6017;
    public static final int MONEY_IS_ZERO = 6018;
    public static final int PAY_ORDER_NULL = 6019;
    public static final int PAY_ORDER_IS_SUCCESS = 6020;
    public static final int BANK_ACCOUNT_IS_EXIST = 6021;
    public static final int STORE_REGION_PATH_ERROR = 6022;
    public static final int NOTIFY_MONEY_ERROR = 6023;
    public static final int PROPERTYINFO_FREEZE_YES = 6024;
    public static final int PROCESSED_RETURN_SUCCESS = 6025;
    public static final int PROPERTYINFO_FREEZE_EXCEPITON = 6026;
    public static final int DEPOSIT_EXIST_UP_PRODUCT = 6027;





    // 商城模块代码 7xxx
    public static final int MESSAGE_HAS_NO_TEMPLATE = 7001;
    public static final int COMMENT_REPEAT_REPLY = 7002;

    //运营 8xxx
    public static final int USER_NOT_LOGIN = 8100;
    public static final int USER_ROLE_EXIST = 8101;
    public static final int ROLE_HAS_USER_RELATE = 8102;
    public static final int ROLE_HAS_PERMISSION = 8103;
    public static final int PUSH_HAS_NOUSER = 8104;
    public static final int ROLE_HAS_NOPERMISSION = 8105;
    public static final int USER_ACCOUNT_DISABLE = 8106;
    public static final int USER_ACCOUNT_EXIST = 8107;

    public static final int STORE_AUDIT_RECORD_NOT_EXIST = 8108;

    public static final int STORE_AUDIT_RECORD_AUDITED = 8109;
    public static final int AD_AUDITED = 8110;

    public static final int AGENT_ACCOUNT_EXIST = 8111;
    public static final int AGENT_MOBILE_EXIST = 8112;



    // 初始化状态码与文字说明
    static {

        // 公共代码 1xxx
        ResultCode.messageMap.put(SUCCESS, "success");
        ResultCode.messageMap.put(FAIL, "fail");
        ResultCode.messageMap.put(NOT_FOUND_DATA, "数据不存在");
        ResultCode.messageMap.put(RESOURCE_NOT_FOUND, "ID对应数据不存在");
        ResultCode.messageMap.put(ID_EMPTY, "ID不能为空");
        ResultCode.messageMap.put(REQUIRED_PARM_EMPTY, "非法参数");
        ResultCode.messageMap.put(SAVE_FAIL, "保存失败");
        ResultCode.messageMap.put(UPDATE_FAIL, "更新失败");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD, "上传图片失败");
        ResultCode.messageMap.put(IMAGE_FORMAT_WRONG_UPLOAD, "上传图片格式错误");
        ResultCode.messageMap.put(RECORD_EXIST, "该记录已经存在");
        ResultCode.messageMap.put(IMAGE_SIZE_ERROR, "上传图片应小于500K");
        ResultCode.messageMap.put(NOT_SEND_SMS_MOBILE, "手机号与验证码不匹配");
        ResultCode.messageMap.put(IMAGE_IS_NULL, "图片格式不正确");
        ResultCode.messageMap.put(MONEY_IS_POINT_2, "金额请保留两位小数");

        ResultCode.messageMap.put(UPLOAD_VEDIO_FAIL, "上传视频失败");
        ResultCode.messageMap.put(UPLOAD_SIZE_BIGER, "上传文件应小于50M");
        ResultCode.messageMap.put(INVITE_FANS_NOT_EXIST, "可邀请的粉丝不存在");
        
        ResultCode.messageMap.put(SMS_SEND_HOUR_LIMIT, "同一手机号1小时内只能发送5次");
        ResultCode.messageMap.put(SMS_SEND_IP_LIMIT, "同一设备24小时内只能发送5次");
        ResultCode.messageMap.put(SMS_SEND_MOBILE_LIMIT, "同一手机号24小时内只能发送5次");
        ResultCode.messageMap.put(VERIFY_PWD_FAIL, "原始密码错误，请重新输入");
        ResultCode.messageMap.put(VERIFY_SMS_CODE_FAIL, "短信验证码错误");
        ResultCode.messageMap.put(VERIFY_PIC_CODE_FAIL, "图片验证码错误");
        ResultCode.messageMap.put(PAY_PWD_NULL, "尚未设置支付密码");
        ResultCode.messageMap.put(PAY_PWD_ERROR, "支付密码错误");
        ResultCode.messageMap.put(ILLEGAL_OPERATION, "非法操作");
        ResultCode.messageMap.put(VERIFY_SMS_CODE_OVERTIME, "短信验证码已失效");
        ResultCode.messageMap.put(INVITER_NO_EXIST, "邀请人不存在，请重新输入");
        ResultCode.messageMap.put(ACCOUNT_EXIST, "该账号已存在");
        ResultCode.messageMap.put(SMS_SEND_FAIL, "短信验证码发送失败，请稍后重试！");

        ResultCode.messageMap.put(ID_CARD_RECORD_EXIST, "该身份证号已经创建过门店");
        ResultCode.messageMap.put(REG_NUMBER_RECORD_EXIST, "该执照已经创建过门店");

        //FastDFS error info
        ResultCode.messageMap.put(FD_FILE_ERROR, "获取上传文件信息异常");
        ResultCode.messageMap.put(FD_FILE_IMG_BIG, "上传图片应小于5M");
        ResultCode.messageMap.put(FD_FILE_CUT_ERROR, "获取视频帧转图片异常");
       
        
        // 用户模块 2xxx
        ResultCode.messageMap.put(MEMBER_WRONG_PWD, "用户名或密码错误");
        ResultCode.messageMap.put(USER_WRONG_ID, "ID不存在");
        ResultCode.messageMap.put(USER_WRONG_IDCARD, "身份证号不正确");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_STORE, "请上传门店照");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_STORE_ENVIRONMENT, "请上传门店环境照");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_LICENSE, "请上传营业执照");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_IDCARD, "手持身份证照");
        ResultCode.messageMap.put(USER_POINT_NOT_ENOUGH, "用户积分不足");
        ResultCode.messageMap.put(MERCHANT_STORE_AUDIT_EXIST, "已经存在未审核记录");
        ResultCode.messageMap.put(MERCHANT_STORE_NO_EXIST, "未查询到门店");
        ResultCode.messageMap.put(MEMBER_NO_EXIST, "用户不存在");
        ResultCode.messageMap.put(BANK_CASH_EXIST, "存在提现申请");
        ResultCode.messageMap.put(FANS_MERCHANT, "已经是商家粉丝");
        ResultCode.messageMap.put(MOBILE_IS_NOT_EXIST, "手机号不存在");
        ResultCode.messageMap.put(MERCHANT_STORE_IS_FAVORITE, "门店已被收藏");
        ResultCode.messageMap.put(ACCOUNT_IS_FREEZE, "账户已被冻结");
        ResultCode.messageMap.put(MAX_USERREDPACKET_COUNT, "单个红包个数不能大于9999");
        ResultCode.messageMap.put(MAX_USERREDPACKET_MONTY, "单个红包金额不能大于50000");
        ResultCode.messageMap.put(MIN_USERREDPACKET_MONTY, "单个红包金额不能小于0.01");
        

        //运营
        ResultCode.messageMap.put(USER_NOT_LOGIN, "用户未登录");
        ResultCode.messageMap.put(USER_ROLE_EXIST, "用户已经存在该权限");
        ResultCode.messageMap.put(ROLE_HAS_USER_RELATE, "角色下面存在关联用户");
        ResultCode.messageMap.put(ROLE_HAS_PERMISSION, "角色下面存在该权限");
        ResultCode.messageMap.put(ROLE_HAS_NOPERMISSION, "没有相关联权限");
        ResultCode.messageMap.put(PUSH_HAS_NOUSER, "没有可推送用户");
        ResultCode.messageMap.put(USER_ACCOUNT_DISABLE, "账号已停用，请联系管理员");
        ResultCode.messageMap.put(USER_ACCOUNT_EXIST, "账号已存在");
        ResultCode.messageMap.put(STORE_AUDIT_RECORD_NOT_EXIST, "审核记录不存在");
        ResultCode.messageMap.put(STORE_AUDIT_RECORD_AUDITED, "该门店已经审核过");
        ResultCode.messageMap.put(AD_AUDITED, "该广告已经审核过");
        ResultCode.messageMap.put(AGENT_ACCOUNT_EXIST, "该账号已经存在");
        ResultCode.messageMap.put(AGENT_MOBILE_EXIST, "该手机号已经存在");



        // 商品模块 3xxx
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_PRODUCT_HEAD, "请上传商品图片");
        ResultCode.messageMap.put(IMAGE_WRONG_UPLOAD_PRODUCT_DETAIL, "请上传商品描述图片");
        ResultCode.messageMap.put(GOODS_PRODUCT_FACORITE_EXIST, "商品已经被收藏");
        ResultCode.messageMap.put(GOODS_PRODUCT_INVENTORY, "存在商品库存为0无法上架");
        ResultCode.messageMap.put(GOODS_PRODUCT_EXIST_ADFLAT, "商品存在广告位中无法下架，请联系后台管理员。");

        // 订单模块 4xxx
        ResultCode.messageMap.put(PRODUCT_EVALUATE_TRUE, "该订单已评价");
        ResultCode.messageMap.put(ORDER_NOT_CANCELED, "订单不能被取消");
        ResultCode.messageMap.put(ORDER_NOT_DELETE, "订单不能被删除");
        ResultCode.messageMap.put(ORDER_NOT_PENDING_PAYMENT, "订单不是待支付状态");
        ResultCode.messageMap.put(ORDER_NOT_RECEIVED, "订单不能确认收货");
        ResultCode.messageMap.put(ORDER_NOT_REFUND, "订单不能被退款");
        ResultCode.messageMap.put(EXCEEDS_RETURN_TIME, "订单超过退货时间");
        ResultCode.messageMap.put(NOT_SHIPPING_STATUS, "订单不是待发货状态");
        ResultCode.messageMap.put(NOT_RETURNED_STATE, "订单退款状态不是待退货状态");
        ResultCode.messageMap.put(NOT_AGREE_TO_APPLY, "订单退款状态不是待确认状态");
        ResultCode.messageMap.put(NOT_REFUNDING, "订单不是退款中状态");
        ResultCode.messageMap.put(ORDER_NOT_FILL_RETURN_ADDRESS, "订单退款状态不是填写退货地址状态");
        ResultCode.messageMap.put(ORDER_NOT_TO_BE_REFUNDED, "订单退款状态不是待退款状态");
        ResultCode.messageMap.put(ORDER_NOT_REFUND_FAILED, "订单退款状态不是退款失败");
        ResultCode.messageMap.put(ORDER_NOT_COMPLETE_STATUS, "订单不是完成状态");
        ResultCode.messageMap.put(PAY_ORDER_FAVORED_AMOUNT_UNEQUAL, "买单优惠金额不正确");
        ResultCode.messageMap.put(ORDER_HAS_BEEN_REFUNDED, "订单已经是退款状态");
        ResultCode.messageMap.put(INVENTORY_SHORTAGE, "库存不足");
        ResultCode.messageMap.put(PRODUCT_HAS_EXPIRED, "商品已经失效");
        ResultCode.messageMap.put(THE_ORDER_IS_BEING_PROCESSED, "订单正在处理");
        ResultCode.messageMap.put(ORDER_CREATION_FAILED, "订单创建失败");
        ResultCode.messageMap.put(MAX_SHOPPING_CART_QUANTITY, "购物车数量已达上限");
        ResultCode.messageMap.put(CAN_NOT_FILL_OUT_THE_RETURN_LOGISTICS, "不能填写退货物流");
        ResultCode.messageMap.put(CAN_NOT_APPLY_FOR_PLATFORM_INTERVENTION, "不能申请平台介入");
        ResultCode.messageMap.put(CAN_NOT_CANCEL_APPLICATION, "不能撤销申请");
        ResultCode.messageMap.put(CAN_NOT_FILL_IN_SHIPPING_LOGISTICS, "不能填写发货物流");
        ResultCode.messageMap.put(CAN_NOT_AGREE_TO_APPLY, "不能同意退款申请");
        ResultCode.messageMap.put(CAN_NOT_AGREE_TO_A_REFUND, "不能同意退款");
        ResultCode.messageMap.put(CAN_NOT_FILL_IN_THE_RETURN_ADDRESS, "不能填写退货地址");
        
        // 广告模块 5xxx
        ResultCode.messageMap.put(AD_POINT_NOT_ENOUGH, "积分不足，请充值");
        ResultCode.messageMap.put(AD_PUT_NOT_TIME, "投放时间没有超过两个星期");
        ResultCode.messageMap.put(AD_FACORITE_EXIST, "广告已被收藏");
        ResultCode.messageMap.put(AD_RED_PACKGE_EXIST, "请等待红包下架");
        ResultCode.messageMap.put(AD_RED_PACKGE_GET, "红包已经领取");
        ResultCode.messageMap.put(AD_PRAISE_PUTED, "抢赞已经结束");
        ResultCode.messageMap.put(AD_CLICK_EXIST, "今天已经看过该广告");
        ResultCode.messageMap.put(AD_PRAISE_POINT_GET, "你已经抢到赞了");
        ResultCode.messageMap.put(AD_CLICK_PUTED, "广告已点击完");
        ResultCode.messageMap.put(AD_RED_PACKGE_PUTED, "红包已下架");
        ResultCode.messageMap.put(AD_BEGIN_TIME_NOT_EXIST, "投放时间不能为空");
        
        // 资产模块 6xxx
        ResultCode.messageMap.put(BANK_ACCOUNT_ERROR, "请重新输入你的银行卡号");
        ResultCode.messageMap.put(CASH_MORE_NUM_MAX_MONEY_ERROR, "提现金额必须大于等于10元");
        ResultCode.messageMap.put(PROPERTY_INFO_NULL, "用户对应财产记录为空");
        ResultCode.messageMap.put(PROPERTY_INFO_OUT_INDEX, "用户对应财产记录错误，存在大于1条记录");
        ResultCode.messageMap.put(PROPERTY_INFO_BALANCE_LESS, "余额不足");
        ResultCode.messageMap.put(PROPERTY_INFO_POINT_LESS, "积分不足，请充值");
        ResultCode.messageMap.put(PROPERTY_CASH_SCALE_NULL, "提现比例系统参数未配置");
        ResultCode.messageMap.put(PROPERTY_CASH_PAY_PWD_ERROR, "支付密码错误");
        ResultCode.messageMap.put(PROPERTY_CASH_BANK_NOT_EXIST, "提交的银行卡ID不存在");
        ResultCode.messageMap.put(PROPERTY_CASH_BANK_NOT_MATCH, "提交的银行卡与用户不匹配");
        ResultCode.messageMap.put(PROPERTY_CASH_USER_INFO_NULL, "提交查询用户冗余信息为空");

        ResultCode.messageMap.put(CASH_BACKAGE_FAILURE_REASON_NULL, "操作失败时原因不能为空");
        ResultCode.messageMap.put(BIZ_TYPE_NULL, "业务类型不能为空");
        ResultCode.messageMap.put(FREEZE_NULL, "冻结资金记录为空");
//        ResultCode.messageMap.put(FREEZE_ROWS_OUT, "冻结资金记录条数已大于1");
        ResultCode.messageMap.put(FREEZE_MONEY_LESS_REFUND_MONEY, "冻结金额不能小于退款金额");
        ResultCode.messageMap.put(DEPOSIT_IN_SYSTEM_DAYS, "不满足申请退保证金要求(保证金核实后90天)");
        ResultCode.messageMap.put(DEPOSIT_EXIST_ING_ORDER, "有未完成订单，完成后才可申请。");
        ResultCode.messageMap.put(MONEY_IS_ZERO, "查询出金额为0");
        ResultCode.messageMap.put(PAY_ORDER_NULL, "买单记录为空");
        ResultCode.messageMap.put(PAY_ORDER_IS_SUCCESS, "重复买单");
        ResultCode.messageMap.put(BANK_ACCOUNT_IS_EXIST, "已经绑定该银行卡");
        ResultCode.messageMap.put(STORE_REGION_PATH_ERROR, "门店所属区域不正确（需要精确到区）");
        ResultCode.messageMap.put(NOTIFY_MONEY_ERROR, "回调金额与表中的金额不一致");
        ResultCode.messageMap.put(PROPERTYINFO_FREEZE_YES, "资金已冻结");
        ResultCode.messageMap.put(PROCESSED_RETURN_SUCCESS, "重复处理，成功返回");
        ResultCode.messageMap.put(PROPERTYINFO_FREEZE_EXCEPITON, "资金出现异常");
        ResultCode.messageMap.put(DEPOSIT_EXIST_UP_PRODUCT, "有未下架商品，完成后才可申请。");


        // 商城模块 7xxx
        ResultCode.messageMap.put(MESSAGE_HAS_NO_TEMPLATE, "未配置对应的消息模板");
        ResultCode.messageMap.put(COMMENT_REPEAT_REPLY, "已经回复了该评价");


    }

    public static String get(int code) {
        return ResultCode.messageMap.get(code);
    }

}
