package com.lawu.eshop.order.srv.domain.extend;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingRefundDetailDO;

public class ShoppingOrderItemExtendDO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * 主键
     */
    private Long id;

    /**
     * 订单id
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
     * 是否评价(0-未评价|1-已评价)
     */
    private Boolean isEvaluation;

    /**
     * 订单项状态(0-待处理|1-待付款|2-待发货|3-待收货|4-交易成功|5-交易关闭|6-退款中)
     */
    private Byte orderStatus;

    /**
     * 退款状态(0-待商家确认|1-填写退货地址|2-待退货|3-待退款|4-退款成功|5-退款失败|6-平台介入)
     */
    private Byte refundStatus;
    
    /**
    * 发送提醒的次数
    */
    private Integer sendTime;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
    
    /**
     * 退款详情
     */
    private ShoppingRefundDetailDO shoppingRefundDetail;
    
    /**
     * 购物订单
     */
    private ShoppingOrderDO shoppingOrder;

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

	public Boolean getIsEvaluation() {
		return isEvaluation;
	}

	public void setIsEvaluation(Boolean isEvaluation) {
		this.isEvaluation = isEvaluation;
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

	public Integer getSendTime() {
		return sendTime;
	}

	public void setSendTime(Integer sendTime) {
		this.sendTime = sendTime;
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

	public ShoppingRefundDetailDO getShoppingRefundDetail() {
		return shoppingRefundDetail;
	}

	public void setShoppingRefundDetail(ShoppingRefundDetailDO shoppingRefundDetail) {
		this.shoppingRefundDetail = shoppingRefundDetail;
	}

	public ShoppingOrderDO getShoppingOrder() {
		return shoppingOrder;
	}

	public void setShoppingOrder(ShoppingOrderDO shoppingOrder) {
		this.shoppingOrder = shoppingOrder;
	}
}