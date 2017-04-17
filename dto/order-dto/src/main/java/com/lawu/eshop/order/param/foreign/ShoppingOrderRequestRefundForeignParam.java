package com.lawu.eshop.order.param.foreign;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 购物订单退货参数 api暴露给app参数
 * 
 * @author Sunny
 * @date 2017/4/11
 */
@ApiModel
public class ShoppingOrderRequestRefundForeignParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 退货原因
	 */
	@ApiModelProperty(required = true, value = "退货原因")
	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
