package com.lawu.eshop.mall.dto.foreign;

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

	public List<ShoppingCartSettlementItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ShoppingCartSettlementItemDTO> items) {
		this.items = items;
	}
    
}