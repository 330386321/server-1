package com.lawu.eshop.order.srv.constants;

/**
 * 异常信息常量类
 * 
 * @author Sunny
 * @date 2017年7月7日
 */
public class ExceptionMessageConstant {
	
	/**  shopping cat **/
	public final static String SHOPPING_CART_DATA_NOT_EXIST = "购物车数据不存在";
	
	public final static String ILLEGAL_OPERATION_SHOPPING_CART = "非法操作购物车";
	
	/**  shopping order **/
	public final static String SHOPPING_ORDER_DATA_NOT_EXIST = "购物订单数据不存在";
	
	public final static String ILLEGAL_OPERATION_SHOPPING_ORDER = "非法操作购物订单";
	
	public final static String ORDER_IS_NOT_PENDING_PAYMENT_STATUS = "订单当前是不是待支付状态";
	
	public final static String ORDER_IS_NOT_OVER = "订单还未结束";
	
	public final static String ORDER_HAS_NOT_BEEN_COMPLETED = "订单还未完成";
	
	/** shopping order item **/
	public final static String IN_THE_ORDER_HAS_BEEN_REFUNDED = "订单已经在退款中";
	
	public final static String THE_CURRENT_ORDER_STATUS_DOES_NOT_MATCH = "当前订单状态不符合";
	
	public final static String PRODUCT_DOES_NOT_SUPPORT_REFUNDS = "商品不支持退款";
	
	public final static String THE_ORDER_EXCEEDS_THE_REFUND_TIME = "订单超过退款时间";
	
}
