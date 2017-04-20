package com.lawu.eshop.order.param.foreign;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商家填写物流信息
 * api接收参数
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
	@NotEmpty(message = "是否需要退货不能为空")
	private Boolean isNeedReturn;
	
	/**
	 * 地址id
	 */
	@ApiModelProperty(value = "地址id", required = false)
	private Long addressId;

	public Boolean getIsNeedReturn() {
		return isNeedReturn;
	}

	public void setIsNeedReturn(Boolean isNeedReturn) {
		this.isNeedReturn = isNeedReturn;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	
}
