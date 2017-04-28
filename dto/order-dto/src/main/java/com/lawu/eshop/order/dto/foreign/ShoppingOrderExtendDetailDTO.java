package com.lawu.eshop.order.dto.foreign;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.order.constants.TransactionPayTypeEnum;

import io.swagger.annotations.ApiModelProperty;

public class ShoppingOrderExtendDetailDTO extends ShoppingOrderExtendQueryDTO implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
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
     * 商品总价
     */
	@ApiModelProperty(value = "商品总价", required = true)
    private BigDecimal commodityTotalPrice;
	
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
	 * 快递公司id
	 */
	@ApiModelProperty(value = "快递公司id", required = false)
	private String expressCompanyId;
	
    /**
     * 快递公司名称
     */
	@ApiModelProperty(value = "快递公司名称", required = false)
    private String expressCompanyName;
	
    /**
     * 运单编号
     */
	@ApiModelProperty(value = "运单编号", required = false)
    private String waybillNum;
	
	/**
	 * 支付方式
	 */
	@ApiModelProperty(value = "支付方式(BALANCE-余额|ALIPAY-支付宝|WX-微信)", required = false)
	private TransactionPayTypeEnum paymentMethod;
	
	/**
	 * 商家编号
	 */
	@ApiModelProperty(value = "商家编号", required = true)
	private String merchantNum;
	
	/**
	 * 倒计时
	 */
	@ApiModelProperty(value = "倒计时", required = false)
	private Long countdown;
	
	/**
	 * 最新更新的物流记录
	 */
	@ApiModelProperty(value = "最新的物流记录", required = false)
	private TraceDTO trace;
	
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

	public BigDecimal getCommodityTotalPrice() {
		return commodityTotalPrice;
	}

	public void setCommodityTotalPrice(BigDecimal commodityTotalPrice) {
		this.commodityTotalPrice = commodityTotalPrice;
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

	public String getExpressCompanyId() {
		return expressCompanyId;
	}

	public void setExpressCompanyId(String expressCompanyId) {
		this.expressCompanyId = expressCompanyId;
	}

	public String getExpressCompanyName() {
		return expressCompanyName;
	}

	public void setExpressCompanyName(String expressCompanyName) {
		this.expressCompanyName = expressCompanyName;
	}

	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	public TransactionPayTypeEnum getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(TransactionPayTypeEnum paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}

	public Long getCountdown() {
		return countdown;
	}

	public void setCountdown(Long countdown) {
		this.countdown = countdown;
	}

	public TraceDTO getTrace() {
		return trace;
	}

	public void setTrace(TraceDTO trace) {
		this.trace = trace;
	}

}