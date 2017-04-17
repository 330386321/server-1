package com.lawu.eshop.order.param.foreign;

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
public class ShoppingRefundDetailAgreeToApplyForeignParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 商家是否同意买家的退货申请
	 */
	@ApiModelProperty(required = true, value = "商家是否同意买家的退货申请")
	private Boolean isAgree;

	public Boolean getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Boolean isAgree) {
		this.isAgree = isAgree;
	}
	
}
