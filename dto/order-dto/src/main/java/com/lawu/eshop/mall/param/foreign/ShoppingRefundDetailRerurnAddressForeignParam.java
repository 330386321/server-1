package com.lawu.eshop.mall.param.foreign;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商家填写物流信息
 * api传递给order-srv参数
 * 
 * @author Sunny
 * @date 2017/4/12
 */
@ApiModel
public class ShoppingRefundDetailRerurnAddressForeignParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否需要退货
	 */
	@ApiModelProperty(value = "是否需要退货", required = true)
	private Boolean isNeedReturn;
	
	/**
	 * 收货人姓名
	 */
	@ApiModelProperty(value = "收货人姓名", required = false)
	private String consigneeName;

	/**
	 * 收货人地址
	 */
	@ApiModelProperty(value = "收货人地址", required = false)
	private String consigneeAddress;

	/**
	 * 收货人手机号码
	 */
	@ApiModelProperty(value = "收货人手机号码", required = false)
	private String consigneeMobile;

	public Boolean getIsNeedReturn() {
		return isNeedReturn;
	}

	public void setIsNeedReturn(Boolean isNeedReturn) {
		this.isNeedReturn = isNeedReturn;
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
	
}
