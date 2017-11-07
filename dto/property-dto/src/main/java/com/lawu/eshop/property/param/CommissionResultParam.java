package com.lawu.eshop.property.param;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * jobs计算提成参数对象
 */
public class CommissionResultParam {

	@NotNull(message = "beforeMoney不能为空")
	private BigDecimal beforeMoney;//实际金额

	@NotNull(message = "commission0不能为空")
	private BigDecimal commission0;//广告收益提成：实际*0.5|买单/购物：实际*1

	@NotNull(message = "currentCommission不能为空")
	private BigDecimal currentCommission;//当前比例（广告不按level累加，买单/购物需要根据level累加）

	@NotNull(message = "actualCommissionScope不能为空")
	private BigDecimal actualCommissionScope;//扣除爱心账户

	@NotNull(message = "loveAccountScale不能为空")
	private BigDecimal loveAccountScale;//爱心账号

	@NotNull(message = "dept不能为空")
	private Integer dept;

	public BigDecimal getBeforeMoney() {
		return beforeMoney;
	}

	public void setBeforeMoney(BigDecimal beforeMoney) {
		this.beforeMoney = beforeMoney;
	}

	public BigDecimal getCommission0() {
		return commission0;
	}

	public void setCommission0(BigDecimal commission0) {
		this.commission0 = commission0;
	}

	public BigDecimal getCurrentCommission() {
		return currentCommission;
	}

	public void setCurrentCommission(BigDecimal currentCommission) {
		this.currentCommission = currentCommission;
	}

	public BigDecimal getActualCommissionScope() {
		return actualCommissionScope;
	}

	public void setActualCommissionScope(BigDecimal actualCommissionScope) {
		this.actualCommissionScope = actualCommissionScope;
	}

	public BigDecimal getLoveAccountScale() {
		return loveAccountScale;
	}

	public void setLoveAccountScale(BigDecimal loveAccountScale) {
		this.loveAccountScale = loveAccountScale;
	}

	public Integer getDept() {
		return dept;
	}

	public void setDept(Integer dept) {
		this.dept = dept;
	}
}
