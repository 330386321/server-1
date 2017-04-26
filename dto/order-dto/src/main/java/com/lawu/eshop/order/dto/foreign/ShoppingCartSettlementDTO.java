package com.lawu.eshop.order.dto.foreign;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ShoppingCartSettlementDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
    /**
     * 总计
     */
    @ApiModelProperty(value = "总计", required = true)
    private BigDecimal total;
    
    /**
     * 用户余额
     */
    @ApiModelProperty(value = "用户余额", required = true)
    private BigDecimal balance;
    
    /**
     * 订单数据 - 分单显示
     */
    @ApiModelProperty(value = "订单数据", required = true)
    private List<ShoppingCartSettlementItemDTO> items;

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<ShoppingCartSettlementItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ShoppingCartSettlementItemDTO> items) {
		this.items = items;
	}
    
}