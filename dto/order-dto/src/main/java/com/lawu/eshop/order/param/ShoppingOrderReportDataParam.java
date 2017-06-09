package com.lawu.eshop.order.param;

/**
 * 
 * @author Sunny
 * @date 2017年5月4日
 */
public class ShoppingOrderReportDataParam {
	
	/**
	 * 创建时间
	 */
	private String gmtCreate;
	
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
