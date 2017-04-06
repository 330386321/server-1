package com.lawu.eshop.mall.param;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车结算api传递给order-srv参数
 * 
 * @author Sunny
 * @date 2017/4/6
 */
public class ShoppingOrderSettlementItemParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 对应的购物车Id(在保存订单之后通过这个删除购物车里面的记录)
	 */
	private Long shoppingCartId;
	
	/**
	 * 商品id
	 */
	private Long productId;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 商品型号id
	 */
	private Long productModelId;

	/**
	 * 商品型号名称
	 */
	private String productModelName;

	/**
	 * 商品特征图片
	 */
	private String productFeatureImage;

	/**
	 * 原价
	 */
	private BigDecimal regularPrice;

	/**
	 * 现价
	 */
	private BigDecimal salesPrice;

	/**
	 * 数量
	 */
	private Integer quantity;
	
	public Long getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(Long shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductModelId() {
		return productModelId;
	}

	public void setProductModelId(Long productModelId) {
		this.productModelId = productModelId;
	}

	public String getProductModelName() {
		return productModelName;
	}

	public void setProductModelName(String productModelName) {
		this.productModelName = productModelName;
	}

	public String getProductFeatureImage() {
		return productFeatureImage;
	}

	public void setProductFeatureImage(String productFeatureImage) {
		this.productFeatureImage = productFeatureImage;
	}

	public BigDecimal getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(BigDecimal regularPrice) {
		this.regularPrice = regularPrice;
	}

	public BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
