package com.lawu.eshop.property.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * <p>
 * Description: 商家发货后14天未确认收货后系统定时确认收货，将订单金额加入商家余额，传参对象
 * </p>
 * @author Yangqh
 * @date 2017年4月25日 下午8:18:33
 *
 */
public class OrderSysJobParam {

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
	
	// 订单实际支付金额,多个已英文逗号分隔
	@NotBlank(message = "orderActualMoney不能为空")
	private String orderActualMoney;

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

	public String getOrderActualMoney() {
		return orderActualMoney;
	}

	public void setOrderActualMoney(String orderActualMoney) {
		this.orderActualMoney = orderActualMoney;
	}

}
