package com.lawu.eshop.order.param;

/**
 * 
 * @author Sunny
 * @date 2017年5月4日
 */
public class ShoppingOrderReportDataParam extends ReportDataParam {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 创建时间
	 */
	private String gmtCreate;
	
	/**
	 * 订单状态
	 */
	private Byte orderStatus;
	
	/**
	 * 允许退款时间(天)
	 */
	private Integer refundRequestTime;

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Byte getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Byte orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getRefundRequestTime() {
		return refundRequestTime;
	}

	public void setRefundRequestTime(Integer refundRequestTime) {
		this.refundRequestTime = refundRequestTime;
	}
	
	
}
