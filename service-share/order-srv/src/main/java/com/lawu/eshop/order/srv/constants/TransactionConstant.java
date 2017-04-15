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
     */
    public static final byte TRADING_SUCCESS = 0x04;

    /**
     * 增加评论记录
     */
    public static final  byte ADD_COMMENT = 0x05;
}
