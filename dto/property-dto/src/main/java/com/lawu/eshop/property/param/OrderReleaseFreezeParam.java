package com.lawu.eshop.property.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * <p>
 * Description:确认收货后7天，系统自动将订单金额加入商家余额
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月14日 上午10:17:47
 *
 */
public class OrderReleaseFreezeParam {

	// 商家编号,多个已英文逗号分隔-顺序与orderIds、accounts、payWays一致
	@NotBlank(message = "userNums不能为空")
	private String userNums;

	// 商品订单ID,多个已英文逗号分隔
	@NotBlank(message = "orderIds不能为空")
	private String orderIds;

	// 商家账号,多个已英文逗号分隔
	@NotBlank(message = "accounts不能为空")
	private String accounts;
	
	//订单支付方式,多个已英文逗号分隔
	@NotNull(message="payWays不能为空")
	private Byte[] payWays;

	public String getUserNums() {
		return userNums;
	}

	public void setUserNums(String userNums) {
		this.userNums = userNums;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public String getAccounts() {
		return accounts;
	}

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	public Byte[] getPayWays() {
		return payWays;
	}

	public void setPayWays(Byte[] payWays) {
		this.payWays = payWays;
	}

}
