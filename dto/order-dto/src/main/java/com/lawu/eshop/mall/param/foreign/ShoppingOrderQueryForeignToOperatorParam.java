package com.lawu.eshop.mall.param.foreign;

import java.io.Serializable;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.mall.constants.ShoppingOrderStatusToMerchantEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * 购物订单查询参数 api暴露给app参数
 * 
 * @author Sunny
 * @date 2017/4/6
 */
public class ShoppingOrderQueryForeignToOperatorParam extends AbstractPageParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单状态
	 */
	@ApiModelProperty(name = "orderStatus", required = false, value = "订单状态|默认全部|PENDING 待处理|PENDING_PAYMENT 待付款|BE_SHIPPED 待发货|TO_BE_RECEIVED 待收货|TRADING_SUCCESS 交易成功|CANCEL_TRANSACTION 交易关闭|REFUNDING 退款中")
	private ShoppingOrderStatusEnum orderStatus;

	/**
	 * 搜索订单的关键字
	 */
	@ApiModelProperty(name = "keyword", required = false, value = "搜索订单的关键字(订单号|收货人名称)")
	private String keyword;

	public ShoppingOrderStatusEnum getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(ShoppingOrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
