package com.lawu.eshop.mall.param;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车结算api传递给order-srv参数
 * 
 * @author Sunny
 * @date 2017/4/6
 */
public class ShoppingOrderSettlementParam implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	private Long memberId;

	/**
	 * 商家id
	 */
	private Long merchantId;

	/**
	 * 商家名称
	 */
	private String merchantName;
	
	/**
	 * 买家留言
	 */
	private String message;
	
    /**
    * 收货人姓名
    */
   private String consigneeName;

   /**
    * 收货人地址
    */
   private String consigneeAddress;

   /**
    * 收货人手机号码
    */
   private String consigneeMobile;
	
	/**
	 * 运费
	 */
	private BigDecimal freightPrice;

	/**
	 * 商品总价
	 */
	private BigDecimal commodityTotalPrice;

	/**
	 * 订单总价
	 */
	private BigDecimal orderTotalPrice;

	/**
	 * 订单项
	 */
	private List<ShoppingOrderSettlementItemParam> items;
	
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public BigDecimal getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(BigDecimal freightPrice) {
		this.freightPrice = freightPrice;
	}

	public BigDecimal getCommodityTotalPrice() {
		return commodityTotalPrice;
	}

	public void setCommodityTotalPrice(BigDecimal commodityTotalPrice) {
		this.commodityTotalPrice = commodityTotalPrice;
	}

	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public List<ShoppingOrderSettlementItemParam> getItems() {
		return items;
	}

	public void setItems(List<ShoppingOrderSettlementItemParam> items) {
		this.items = items;
	}
	
}