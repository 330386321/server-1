package com.lawu.eshop.mall.param.foreign;

import java.io.Serializable;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * 购物订单查询参数 api暴露给app参数
 * 
 * @author Sunny
 * @date 2017/4/6
 */
public class ShoppingOrderQueryForeignParam extends AbstractPageParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单状态
	 */
	@ApiModelProperty(name = "orderStatus", required = false, value = "订单状态<br/>默认全部<br>PENDING_PAYMENT 待付款<br/>BE_SHIPPED 待发货<br/>TRADING_SUCCESS 交易成功<br/>CANCEL_TRANSACTION 交易取消<br/>TO_BE_CONFIRMED 待商家确认<br/>TO_BE_RETURNED 待退货<br/>TO_BE_REFUNDED 待退款<br/>REFUND_SUCCESSFULLY 退款成功")
	private ShoppingOrderStatusEnum orderStatus;

	/**
	 * 是否评价
	 */
	@ApiModelProperty(name = "isEvaluation", required = false, value = "是否评价(只针对交易完成的订单)")
	private Boolean isEvaluation;
	
	/**
	 * 搜索订单的关键字
	 */
	@ApiModelProperty(name = "keyword", required = false, value = "搜索订单的关键字(订单号|商品名称)")
	private String keyword;

	public ShoppingOrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ShoppingOrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Boolean getIsEvaluation() {
		return isEvaluation;
	}

	public void setIsEvaluation(Boolean isEvaluation) {
		this.isEvaluation = isEvaluation;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
