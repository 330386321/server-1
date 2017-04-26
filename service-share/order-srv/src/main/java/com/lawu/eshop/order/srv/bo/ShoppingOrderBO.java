package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.order.constants.CommissionStatusEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.constants.StatusEnum;
import com.lawu.eshop.order.constants.TransactionPayTypeEnum;

public class ShoppingOrderBO implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	/**
     * 主键
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long memberId;
    
    /**
    * 会员编号
    */
    private String memberNum;
    
    /**
     * 商家ID
     */
    private Long merchantId;
    
    /**
    * 商家编号
    */
    private String merchantNum;

    /**
     * 商家名称
     */
    private String merchantName;

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
     * 订单备注(退货理由)
     */
    private String remark;

    /**
     * 买家留言
     */
    private String message;

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
	 * 实际支付给商家的金额
	 */
	private BigDecimal actualAmount;

	/**
	 * 是否计算过提成(0-没有计算过提成|1-计算过提成)
	 */
	private CommissionStatusEnum commissionStatus;
    
    /**
     * 订单的总状态
     */
    private ShoppingOrderStatusEnum orderStatus;

    /**
     * 状态(0删除1正常)
     */
    private StatusEnum status;
    
    /**
    * 发送提醒的次数
    */
    private Integer sendTime;

    /**
     * 是否支持无理由退货,0否 1是
     */
    private Boolean isNoReasonReturn;
    
    /**
     * 是否自动收货(0-否|1-是)
     */
    private Boolean isAutomaticReceipt;
    
    /**
    * 对应的购物车相应的id(多个id用,分隔)
    */
   private String shoppingCartIdsStr;
    
    /**
     * 订单编号
     */
    private String orderNum;

	/**
	 * 支付方式(1-余额 2-微信 3-支付宝)
	 */
	private TransactionPayTypeEnum paymentMethod;

	/**
	 * 第三方支付交易号
	 */
	private String thirdNumber;
    
    /**
     * 运单编号
     */
    private String waybillNum;

    /**
     * 快递公司id
     */
    private Integer expressCompanyId;

    /**
     * 快递公司编码
     */
    private String expressCompanyCode;

    /**
     * 快递公司名称
     */
    private String expressCompanyName;
    
	/**
	 * 计算提成的时间
	 */
	private Date gmtCommission;
    
    /**
     * 付款时间
     */
    private Date gmtPayment;

    /**
     * 发货时间
     */
    private Date gmtTransport;

    /**
     * 交易时间
     */
    private Date gmtTransaction;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public CommissionStatusEnum getCommissionStatus() {
		return commissionStatus;
	}

	public void setCommissionStatus(CommissionStatusEnum commissionStatus) {
		this.commissionStatus = commissionStatus;
	}

	public ShoppingOrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ShoppingOrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Integer getSendTime() {
		return sendTime;
	}

	public void setSendTime(Integer sendTime) {
		this.sendTime = sendTime;
	}

	public Boolean getIsNoReasonReturn() {
		return isNoReasonReturn;
	}

	public void setIsNoReasonReturn(Boolean isNoReasonReturn) {
		this.isNoReasonReturn = isNoReasonReturn;
	}

	public Boolean getIsAutomaticReceipt() {
		return isAutomaticReceipt;
	}

	public void setIsAutomaticReceipt(Boolean isAutomaticReceipt) {
		this.isAutomaticReceipt = isAutomaticReceipt;
	}

	public String getShoppingCartIdsStr() {
		return shoppingCartIdsStr;
	}

	public void setShoppingCartIdsStr(String shoppingCartIdsStr) {
		this.shoppingCartIdsStr = shoppingCartIdsStr;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public TransactionPayTypeEnum getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(TransactionPayTypeEnum paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getThirdNumber() {
		return thirdNumber;
	}

	public void setThirdNumber(String thirdNumber) {
		this.thirdNumber = thirdNumber;
	}

	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	public Integer getExpressCompanyId() {
		return expressCompanyId;
	}

	public void setExpressCompanyId(Integer expressCompanyId) {
		this.expressCompanyId = expressCompanyId;
	}

	public String getExpressCompanyCode() {
		return expressCompanyCode;
	}

	public void setExpressCompanyCode(String expressCompanyCode) {
		this.expressCompanyCode = expressCompanyCode;
	}

	public String getExpressCompanyName() {
		return expressCompanyName;
	}

	public void setExpressCompanyName(String expressCompanyName) {
		this.expressCompanyName = expressCompanyName;
	}

	public Date getGmtCommission() {
		return gmtCommission;
	}

	public void setGmtCommission(Date gmtCommission) {
		this.gmtCommission = gmtCommission;
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

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
    
}