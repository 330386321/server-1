package com.lawu.eshop.order.srv.constants;

/**
 * 分布式事务业务类型
 * 
 * @author Sunny
 * @date 2017年4月14日
 */
public class TransactionConstant {

    /**
     * 买单
     */
    public static final byte PAYORDER = 0x01;
    
    /**
     * 创建购物订单
     */
    public static final byte CREATE_ORDER = 0x02;
    
    /**
     * 取消购物订单
     */
    public static final byte CANCEL_ORDER = 0x03;
    
    /**
     * 确认收货
     * 用户手动确认收货
     */
    public static final byte TRADING_SUCCESS = 0x04;

    /**
     * 定时任务自动评论
     */
    public static final  byte AUTO_COMMENT = 0x05;
    
    /**
     * 商家同意退款
     */
    public static final  byte AGREE_TO_REFUND = 0x06;
    
    /**
     * 提醒卖家发货
     */
    public static final  byte REMIND_SHIPMENTS = 0x07;
    
    /**
     * 删除订单
     */
    public static final  byte DELETE_SHOPPING_ORDER = 0x08;
    
    /**
     * 退款中-待商家处理
	 * 退款类型-退款
	 * 商家处理超时事务
     */
    public static final  byte TO_BE_CONFIRMED_FOR_REFUND_REMIND = 0x09;
    
    /**
     * 退款中-待商家处理
	 * 退款类型-退货退款
	 * 商家处理超时事务
     */
    public static final  byte TO_BE_CONFIRMED_FOR_RETURN_REFUND_REMIND = 0x0a;
    
    /**
     * 退款中-商家拒绝退款
	 * 买家处理超时
	 * 发送提醒事务
     */
    public static final  byte REFUND_FAILED_REMIND = 0x0b;
    
    /**
	 * 退款中-商家填写退货地址
	 * 商家处理超时
	 * 发送提醒事务
     */
    public static final  byte FILL_RETURN_ADDRESS_REMIND = 0x0c;
    
    /**
	 * 退款中-等待买家退货
	 * 买家处理超时
	 * 发送提醒事务
     */
    public static final  byte TO_BE_RETURN_REMIND = 0x0d;
    
    /**
	 * 退款中-等待商家退款
	 * 商家处理超时
	 * 发送提醒事务
     */
    public static final  byte TO_BE_RETURN_REFUND = 0x0e;
    
    /**
     * 创建购物订单-成为商家粉丝
     */
    public static final byte CREATE_ORDER_FANS = 0x0f;
    
    /**
     * 商家同意退款-删除评论
     */
    public static final  byte AGREE_TO_REFUND_DELETE_COMMENT = 0x10;
    
    /**
     * 确认收货
     * 增加销量
     */
    public static final byte TRADING_SUCCESS_INCREASE_SALES = 0x11;
    
    /**
     * 确认收货之后，超过退款时间，打款给商家
     */
    public static final byte TRADING_SUCCESS_PAYMENTS_TO_MERCHANT = 0x12;
}
