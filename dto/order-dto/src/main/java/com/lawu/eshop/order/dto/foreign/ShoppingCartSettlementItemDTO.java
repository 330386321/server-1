package com.lawu.eshop.order.dto.foreign;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ShoppingCartSettlementItemDTO implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
    /**
     * 小计
     */
    @ApiModelProperty(value = "小计", required = true)
    private BigDecimal subtotal;
    
    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量", required = true)
    private Integer productNumber;
    
    /**
     * 订单项数据
     */
    @ApiModelProperty(value = "订单项数据", required = true)
    private List<MemberShoppingCartDTO> items;

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}

	public List<MemberShoppingCartDTO> getItems() {
		return items;
	}

	public void setItems(List<MemberShoppingCartDTO> items) {
		this.items = items;
	} 
    
}