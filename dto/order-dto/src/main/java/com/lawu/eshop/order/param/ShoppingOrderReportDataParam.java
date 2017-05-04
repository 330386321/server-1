package com.lawu.eshop.order.param;

import java.io.Serializable;

/**
 * 
 * @author Sunny
 * @date 2017年5月4日
 */
public class ShoppingOrderReportDataParam implements Serializable {
	
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
	
	/**
	 * 商家id
	 */
	private Long merchantId;
	
	/**
	 * 类型
	 */
	private Byte flag;

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

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Byte getFlag() {
		return flag;
	}

	public void setFlag(Byte flag) {
		this.flag = flag;
	}
	
}
