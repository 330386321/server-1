package com.lawu.eshop.order.srv.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.mall.constants.ShoppingRefundTypeEnum;

public class ShoppingRefundDetailBO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 购物订单项id
	 */
	private Long shoppingOrderItemId;

	/**
	 * 退款类型
	 */
	private ShoppingRefundTypeEnum type;

	/**
	 * 退货原因
	 */
	private String reason;

	/**
	 * 退款金额
	 */
	private BigDecimal amount;

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
	 * 物流编号
	 */
	private String waybillNum;

	/**
	 * 退款时间
	 */
	private Date gmtRefund;

	/**
	 * 商家确认时间
	 */
	private Date gmtConfirmed;

	/**
	 * 商家填写退货地址时间
	 */
	private Date gmtFill;

	/**
	 * 买家提交退货物流时间
	 */
	private Date gmtSubmit;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 更新时间
	 */
	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShoppingOrderItemId() {
		return shoppingOrderItemId;
	}

	public void setShoppingOrderItemId(Long shoppingOrderItemId) {
		this.shoppingOrderItemId = shoppingOrderItemId;
	}

	public ShoppingRefundTypeEnum getType() {
		return type;
	}

	public void setType(ShoppingRefundTypeEnum type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	public Date getGmtRefund() {
		return gmtRefund;
	}

	public void setGmtRefund(Date gmtRefund) {
		this.gmtRefund = gmtRefund;
	}

	public Date getGmtConfirmed() {
		return gmtConfirmed;
	}

	public void setGmtConfirmed(Date gmtConfirmed) {
		this.gmtConfirmed = gmtConfirmed;
	}

	public Date getGmtFill() {
		return gmtFill;
	}

	public void setGmtFill(Date gmtFill) {
		this.gmtFill = gmtFill;
	}

	public Date getGmtSubmit() {
		return gmtSubmit;
	}

	public void setGmtSubmit(Date gmtSubmit) {
		this.gmtSubmit = gmtSubmit;
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