package com.lawu.eshop.order.dto.foreign;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.lawu.eshop.order.constants.ShoppingRefundTypeEnum;

import io.swagger.annotations.ApiModelProperty;

public class ShoppingRefundDetailDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 退款详情id
	 */
	@ApiModelProperty(value = "退款详情id", required = true)
	private Long id;

	/**
	 * 退款类型
	 */
	@ApiModelProperty(value = "退款类型", required = true)
	private ShoppingRefundTypeEnum type;

	/**
	 * 退货原因
	 */
	@ApiModelProperty(value = "退款类型", required = true)
	private String reason;

	/**
	 * 退款金额
	 */
	@ApiModelProperty(value = "退款类型", required = true)
	private BigDecimal amount;

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
	 * 快递公司名称
	 */
	@ApiModelProperty(value = "快递公司名称", required = true)
	private String expressCompanyName;

	/**
	 * 物流编号
	 */
	@ApiModelProperty(value = "物流编号", required = true)
	private String waybillNum;
	
    /**
    * 商家是否同意退货申请
    */
	@ApiModelProperty(value = "商家是否同意退货申请", required = true)
	private Boolean isAgree;
	
	/**
	 * 退款时间
	 */
	@ApiModelProperty(value = "退款时间", required = true)
	private Date gmtRefund;

	/**
	 * 商家确认时间
	 */
	@ApiModelProperty(value = "商家确认时间", required = true)
	private Date gmtConfirmed;

	/**
	 * 商家填写退货地址时间
	 */
	@ApiModelProperty(value = "商家填写退货地址时间", required = true)
	private Date gmtFill;

	/**
	 * 买家提交退货物流时间
	 */
	@ApiModelProperty(value = "买家提交退货物流时间", required = true)
	private Date gmtSubmit;
	
    /**
    * 平台介入时间
    */
	@ApiModelProperty(value = "平台介入时间", required = true)
    private Date gmtIntervention;
	
	/**
	 * 退款申请时间
	 */
	@ApiModelProperty(value = "退款申请时间", required = true)
	private Date gmtCreate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Boolean isAgree) {
		this.isAgree = isAgree;
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

	public Date getGmtIntervention() {
		return gmtIntervention;
	}

	public void setGmtIntervention(Date gmtIntervention) {
		this.gmtIntervention = gmtIntervention;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

}