package com.lawu.eshop.mall.dto.foreign;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;

import io.swagger.annotations.ApiModelProperty;

public class ShoppingOrderExtendDetailDTO implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	/**
     * 主键 
     */
	@ApiModelProperty(value = "购物订单id", required = true)
    private Long id;

    /**
     * 商家ID
     */
	@ApiModelProperty(value = "商家id", required = true)
    private Long merchantId;

    /**
     * 商家名称
     */
	@ApiModelProperty(value = "商家名称", required = true)
    private String merchantName;

    /**
     * 收货人姓名
     */
	@ApiModelProperty(value = "收货人姓名", required = true)
    private String consigneeName;

    /**
     * 收货人地址
     */
	@ApiModelProperty(value = "收货人地址", required = true)
    private String consigneeAddress;

    /**
     * 收货人手机号码
     */
	@ApiModelProperty(value = "收货人手机号码", required = true)
    private String consigneeMobile;
    
    /**
     * 运费
     */
	@ApiModelProperty(value = "运费", required = true)
    private BigDecimal freightPrice;

    /**
     * 商品总价
     */
	@ApiModelProperty(value = "商品总价", required = true)
    private BigDecimal commodityTotalPrice;

    /**
     * 订单总价
     */
	@ApiModelProperty(value = "商品总价", required = true)
    private BigDecimal orderTotalPrice;

    /**
     * 订单的总状态
     */
	@ApiModelProperty(value = "订单状态|PENDING_PAYMENT 待付款|BE_SHIPPED 待发货|TO_BE_RECEIVED 待收货|TRADING_SUCCESS 交易成功|CANCEL_TRANSACTION 交易关闭|REFUNDING 退款中", required = true)
    private ShoppingOrderStatusEnum orderStatus;

    /**
    * 是否支持无理由退货,0否 1是
    */
	@ApiModelProperty(value = "是否支持无理由退货(false 不支持|true 支持)", required = true)
   private Boolean isNoReasonReturn;
    
    /**
     * 订单编号
     */
	@ApiModelProperty(value = "订单编号", required = true)
    private String orderNum;

    /**
     * 付款时间
     */
	@ApiModelProperty(value = "付款时间", required = true)
    private Date gmtPayment;

    /**
     * 发货时间
     */
	@ApiModelProperty(value = "发货时间", required = true)
    private Date gmtTransport;

    /**
     * 交易时间
     */
	@ApiModelProperty(value = "交易时间", required = true)
    private Date gmtTransaction;

    /**
     * 创建时间
     */
	@ApiModelProperty(value = "创建时间", required = true)
    private Date gmtCreate;
    
    /**
     * 订单项
     */
	@ApiModelProperty(value = "订单项", required = true)
    private List<ShoppingOrderItemDTO> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public ShoppingOrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ShoppingOrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Boolean getIsNoReasonReturn() {
		return isNoReasonReturn;
	}

	public void setIsNoReasonReturn(Boolean isNoReasonReturn) {
		this.isNoReasonReturn = isNoReasonReturn;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Date getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(Date gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public Date getGmtTransport() {
		return gmtTransport;
	}

	public void setGmtTransport(Date gmtTransport) {
		this.gmtTransport = gmtTransport;
	}

	public Date getGmtTransaction() {
		return gmtTransaction;
	}

	public void setGmtTransaction(Date gmtTransaction) {
		this.gmtTransaction = gmtTransaction;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public List<ShoppingOrderItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ShoppingOrderItemDTO> items) {
		this.items = items;
	}
	
}