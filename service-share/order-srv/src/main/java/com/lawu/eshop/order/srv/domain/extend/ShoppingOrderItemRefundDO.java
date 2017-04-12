package com.lawu.eshop.order.srv.domain.extend;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShoppingOrderItemRefundDO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 购物订单项id
	 */
	private Long id;

	/**
	 * 购物订单id
	 */
	private Long shoppingOrderId;

	/**
	 * 商品id
	 */
	private Long productId;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 商品型号id
	 */
	private Long productModelId;

	/**
	 * 商品型号名称
	 */
	private String productModelName;

	/**
	 * 商品特征图片
	 */
	private String productFeatureImage;

	/**
	 * 原价
	 */
	private BigDecimal regularPrice;

	/**
	 * 现价
	 */
	private BigDecimal salesPrice;

	/**
	 * 数量
	 */
	private Integer quantity;

	/**
	 * 订单项状态(0-待付款|1-待发货|2-待收货|3-交易成功|4-交易关闭|5-退款中)
	 */
	private Byte orderStatus;

	/**
	 * 退款状态(0-待商家确认|1-待退货|2-待退款|3-退款成功|4-退款失败|5-平台介入)
	 */
	private Byte refundStatus;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	/**
	 * 购物退款详情id
	 */
	private Long shoppingRefundDetailId;
	
	/**
	 * 商家ID
	 */
	private Long merchantId;

	/**
	 * 商家名称
	 */
	private String merchantName;
	
	/**
	 * 退款类型(0-退款|1-退货退款)
	 */
	private Byte type;

	/**
	 * 退款金额
	 */
	private BigDecimal amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShoppingOrderId() {
		return shoppingOrderId;
	}

	public void setShoppingOrderId(Long shoppingOrderId) {
		this.shoppingOrderId = shoppingOrderId;
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

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductModelId() {
		return productModelId;
	}

	public void setProductModelId(Long productModelId) {
		this.productModelId = productModelId;
	}

	public String getProductModelName() {
		return productModelName;
	}

	public void setProductModelName(String productModelName) {
		this.productModelName = productModelName;
	}

	public String getProductFeatureImage() {
		return productFeatureImage;
	}

	public void setProductFeatureImage(String productFeatureImage) {
		this.productFeatureImage = productFeatureImage;
	}

	public BigDecimal getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(BigDecimal regularPrice) {
		this.regularPrice = regularPrice;
	}

	public BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Byte getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Byte orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Byte getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Byte refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Long getShoppingRefundDetailId() {
		return shoppingRefundDetailId;
	}

	public void setShoppingRefundDetailId(Long shoppingRefundDetailId) {
		this.shoppingRefundDetailId = shoppingRefundDetailId;
	}
	
}