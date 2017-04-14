package com.lawu.eshop.property.param;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.lawu.eshop.property.constants.TransactionPayTypeEnum;

/**
 * 
 * <p>
 * Description: 商家同意退款请求srv参数对象
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月14日 下午1:02:20
 *
 */
public class OrderRefundDataParam extends OrderRefundParam {

	// 商家用户编号
	@NotBlank(message = "userNum不能为空")
	private String userNum;

	// 会员用户编号
	@NotBlank(message = "sideUserNum不能为空")
	private String sideUserNum;

	// 商品订单ID
	@NotBlank(message = "orderId不能为空")
	private String orderId;

	// 商家订单中需要退款的订单项ID-支持多个，已英文逗号分隔
	@NotBlank(message = "orderItemIds不能为空")
	private String orderItemIds;

	// 退款总金额-如果是多个order_item退款时为item金额汇总
	@NotBlank(message = "refundMoney不能为空")
	@Pattern(regexp = "^\\d{0,8}\\.{0,1}(\\d{1,2})?$", message = "refundMoney格式错误要求数字或小数位不超过2位")
	private String refundMoney;

	// 是否是最后一个order_item退款
	@NotNull(message = "isLast不能为空")
	private boolean isLast;

	// 支付方式
	@NotNull(message = "transactionPayTypeEnum不能为空")
	private TransactionPayTypeEnum transactionPayTypeEnum;

	// 第三方平台订单号
	@NotBlank(message = "tradeNo不能为空")
	private String tradeNo;

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderItemIds() {
		return orderItemIds;
	}

	public void setOrderItemIds(String orderItemIds) {
		this.orderItemIds = orderItemIds;
	}

	public String getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(String refundMoney) {
		this.refundMoney = refundMoney;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public TransactionPayTypeEnum getTransactionPayTypeEnum() {
		return transactionPayTypeEnum;
	}

	public void setTransactionPayTypeEnum(TransactionPayTypeEnum transactionPayTypeEnum) {
		this.transactionPayTypeEnum = transactionPayTypeEnum;
	}

	public String getSideUserNum() {
		return sideUserNum;
	}

	public void setSideUserNum(String sideUserNum) {
		this.sideUserNum = sideUserNum;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

}
