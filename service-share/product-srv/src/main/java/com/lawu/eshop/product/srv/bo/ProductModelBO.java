package com.lawu.eshop.product.srv.bo;

import java.math.BigDecimal;

/**
 * 产品型号BO
 *
 * @author Yangqh
 * @date 2017/3/22
 */
public class ProductModelBO {

	private Long id;
    private String name;
    private BigDecimal originalPrice;
    private BigDecimal price;
    private Integer inventory;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getInventory() {
		return inventory;
	}
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	
	
    
}
