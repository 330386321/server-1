package com.lawu.eshop.order.srv.constants;

/**
 * 数据库配置表name常量
 * 
 * @author Sunny
 * @date 2017年4月15日
 */
public class PropertyNameConstant {
	
	/**
	 * 退货申请时间
	 */
	public final static String REFUND_REQUEST_TIME = "refund_request_time";
	
	/**
	 * 自动评价时间
	 */
	public final static String AUTOMATIC_EVALUATION = "automatic_evaluation";
	
	/**
	 * 自动取消未付款订单时间
	 */
	public final static String AUTOMATIC_CANCEL_ORDER = "automatic_cancel_order";

	/**
	 * 自动提醒卖家发货时间
	 */
	public final static String AUTOMATIC_REMIND_SHIPMENTS = "automatic_remind_shipments";
	
	/**
	 * 自动收货时间
	 */
	public final static String AUTOMATIC_RECEIPT = "automatic_receipt";
	
	/**
	 * 删除订单时间
	 */
	public final static String DELETE_ORDER = "delete_order";
	
	/**
	 * 买家申请退款，提醒处理商家时间
	 */
	public final static String TO_BE_CONFIRMED_FOR_REFUND_REMIND_TIME = "to_be_confirmed_for_refund_remind_time";
	
	/**
	 * 买家申请退款，商家未处理，平台自动退款时间
	 */
	public final static String TO_BE_CONFIRMED_FOR_REFUND_REFUND_TIME = "to_be_confirmed_for_refund_refund_time";
	
}
