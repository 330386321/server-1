package com.lawu.eshop.mq.constants;

/**
 * @author Leach
 * @date 2017/4/11
 */
public class MqConstant {


    /**topic定义*/

    /**
     * ad-srv模块MQ生产者topic
     */
    public static final String TOPIC_AD_SRV = "ad_srv";

    /**
     * mall-srv模块MQ生产者topic
     */
    public static final String TOPIC_MALL_SRV = "mall_srv";

    /**
     * order-srv模块MQ生产者topic
     */
    public static final String TOPIC_ORDER_SRV = "order_srv";

    /**
     * product-srv模块MQ生产者topic
     */
    public static final String TOPIC_PRODUCT_SRV = "product_srv";

    /**
     * property-srv模块MQ生产者topic
     */
    public static final String TOPIC_PROPERTY_SRV = "property_srv";

    /**
     * user-srv模块MQ生产者topic
     */
    public static final String TOPIC_USER_SRV = "user_srv";

    /******************************************/
    /*******ad-srv模块tag **********/
   public static final String TAG_AD_ME_CUT_POINT = "ad_me_cutPoint";
    
    public static final String TAG_AD_ME_ADD_POINT = "ad_me_addPoint";
    
    public static final String TAG_AD_USER_ADD_POINT = "ad_user_addPoint";
    
    public static final String TAG_RP_ME_CUT_POINT = "rp_me_cutPoint";

    public static final String TAG_AD_USER_CLICK_POINT = "rp_user_cutPoint";
    
    public static final String TAG_AD_USER_SWEEP_RED = "rp_user_sweep_red";


    /******************************************/
    /*******mall-srv模块tag **********/
    /**
     * 评论商品时发布的MQ消息
     */
    public static final String TAG_COMMENT_PRODUCT = "comment_product";
    /**
     * 评论商家时发布MQ消息
     */
    public static final String TAG_COMMENT_MERCHANT = "comment_merchant";

    /**
     * 增加站内消息发推送
     */
    public static final String TAG_GTPUSH = "gtpush";

    /**
     * 增加站内消息发推送所有用户
     */
    public static final String TAG_GTPUSHALL = "pushall";
    /**
     * 增加站内消息发推送所有区域内用户
     */
    public static final String TAG_GTPUSH_AREA = "push_area";


    /******************************************/
    /*******order-srv模块tag **********/

    /**
     * 买单更改状态发布MQ增加买单笔数
     */
    public static final String TAG_BUY_NUMBERS = "buy_numbers";
    
    /**
     * 创建购物订单时发布的MQ消息
     */
    public static final String TAG_CREATE_ORDER = "create_order";
    
    /**
     * 取消购物订单时发布的MQ消息
     */
    public static final String TAG_CANCEL_ORDER = "cancel_order";
    
    /**
     * 确认收货时发布的MQ消息
     */
    public static final String TAG_TRADING_SUCCESS = "trading_success";

    /**
     * 定时器发送增加评论信息消息
     */
    public static final String TAG_AUTO_COMMENT = "auto_comment";
    
    /**
     * 商家同意退款发布的MQ消息
     */
    public static final String TAG_AGREE_TO_REFUND = "agree_to_refund";
    
    /**
     * 商家同意退款
     * 发布给mall模块的MQ消息，删除商品评论
     */
    public static final String TAG_AGREE_TO_REFUND_DELETE_COMMENT = "agree_to_refund_delete_comment";
    
    /**
     * 提醒卖家发货时发布的MQ消息
     */
    public static final String TAG_REMIND_SHIPMENTS = "remind_shipments";
    
    /**
     * 删除购物订单时发布的MQ消息
     */
    public static final String TAG_DELETE_SHOPPING_ORDER = "delete_shopping_order";
    
    /**
     * 退款中-待商家处理
	 * 退款类型-退款
	 * 商家处理超时发布的MQ消息
     */
    public static final String TAG_TO_BE_CONFIRMED_FOR_REFUND_REMIND = "to_be_confirmed_for_refund_remind";
    
    /**
     * 退款中-待商家处理
	 * 退款类型-退货退款
	 * 商家处理超时发布的MQ消息
     */
    public static final String TAG_TO_BE_CONFIRMED_FOR_RETURN_REFUND_REMIND = "to_be_confirmed_for_return_refund_remind";
    
    /**
     * 退款中-商家拒绝退款
	 * 买家处理超时发布的MQ消息
	 * 发送到mall模块，提醒买家操作
     */
    public static final String TAG_REFUND_FAILED_REMIND = "refund_failed_remind";
    
    /**
     * 退款中-商家填写退货地址
	 * 商家处理超时发布的MQ消息
	 * 发送到mall模块，提醒商家操作
     */
    public static final String TAG_FILL_RETURN_ADDRESS_REMIND = "fill_return_address_remind";
    
    /**
     * 退款中-等待买家退货
	 * 买家处理超时发布的MQ消息
	 * 发送到mall模块，提醒商家操作
     */
    public static final String TAG_TO_BE_RETURN_REMIND = "to_be_return_remind";
    
    /**
     * 退款中-等待商家退款
	 * 买家处理超时发布的MQ消息
	 * 发送到mall模块，提醒买家操作
     */
    public static final String TAG_TO_BE_REFUND_REMIND = "to_be_refund_remind";
    
    /**
     * 创建购物订单时发布的MQ消息
     * 发送到user模块，用户 成为商家粉丝
     */
    public static final String TAG_CREATE_ORDER_FANS = "create_order_fans";
    
    /**
     * 确认收货时发布的MQ消息
     * 发送到产品模块，添加销量
     */
    public static final String TAG_TRADING_SUCCESS_INCREASE_SALES = "trading_success_increase_sales";
    
    /**
     * 确认收货之后执行定时任务，如果超过退款时间发布的MQ消息
     * 发送到资产模块，打款给商家
     */
    public static final String TAG_TRADING_SUCCESS_PAYMENTS_TO_MERCHANT = "trading_success_payments_to_merchant";
    
    /**
     * 订单付款成功发布的MQ消息
     * 发送到商城模块，推送给商家
     */
    public static final String TAG_PAYMENT_SUCCESSFUL_PUSH_TO_MERCHANT = "payment_successful_push_to_merchant";
    
    /**
     * 定时任务
     * 提醒买家有未支付的订单-发布的MQ消息
     * 发送到商城模块，推送给买家
     */
    public static final String TAG_ORDER_NO_PAYMENT_PUSH_TO_MEMBER = "order_no_payment_push_to_member";
    
    /******************************************/
    /*******product-srv模块tag **********/
    public static final String TAG_DEL_COMMENT = "del_comment";

    /******************************************/
    /*******property-srv模块tag **********/

    /**
     * 买单时支付成功发布的MQ消息改状态
     */
    public static final String TAG_PAYORDER = "payorder";
    
    /**
     * 购物订单支付时发布的MQ消息改状态
     */
    public static final String TAG_PAY_SHOPPING_ORDER = "pay_shopping_order";

    /**
     * 缴纳保证金发布的MQ消息改门店状态
     */
    public static final String TAG_HANDLE_DEPOSIT = "handle_deposit";

    /******************************************/
    /*******user-srv模块tag **********/

    /**
     * 注册时发布的MQ消息tag
     */
    public static final String TAG_REG = "reg";



}

