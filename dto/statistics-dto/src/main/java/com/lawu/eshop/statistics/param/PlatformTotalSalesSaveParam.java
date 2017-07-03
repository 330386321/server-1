package com.lawu.eshop.statistics.param;

import java.math.BigDecimal;

import com.lawu.eshop.statistics.constants.ReportTypeEnum;

/**
 * 平台总销量保存参数
 * 
 * @author Sunny
 * @date 2017年6月30日
 */
public class PlatformTotalSalesSaveParam {
	
	private ReportTypeEnum type;
	
	private BigDecimal payOrderAmount;
	
	private BigDecimal shoppingOrderAmount;

	public ReportTypeEnum getType() {
		return type;
	}

	public void setType(ReportTypeEnum type) {
		this.type = type;
	}

	public BigDecimal getPayOrderAmount() {
		return payOrderAmount;
	}

	public void setPayOrderAmount(BigDecimal payOrderAmount) {
		this.payOrderAmount = payOrderAmount;
	}

	public BigDecimal getShoppingOrderAmount() {
		return shoppingOrderAmount;
	}

	public void setShoppingOrderAmount(BigDecimal shoppingOrderAmount) {
		this.shoppingOrderAmount = shoppingOrderAmount;
	}
	
}
