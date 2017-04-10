package com.lawu.eshop.mall.param.foreign;

import java.io.Serializable;

import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * 购物订单更新状态参数 api暴露给app参数
 * 
 * @author Sunny
 * @date 2017/4/10
 */
public class ShoppingOrderUpdateOrderStatusForeignParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单状态
	 */
	@ApiModelProperty(name = "orderStatus", required = false, value = "订单状态|默认全部<br>PENDING_PAYMENT 待付款|BE_SHIPPED 待发货|TRADING_SUCCESS 交易成功|CANCEL_TRANSACTION 交易取消|TO_BE_CONFIRMED 待商家确认|TO_BE_RETURNED 待退货|TO_BE_REFUNDED 待退款|REFUND_SUCCESSFULLY 退款成功")
	private ShoppingOrderStatusEnum orderStatus;


	public ShoppingOrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ShoppingOrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

}
