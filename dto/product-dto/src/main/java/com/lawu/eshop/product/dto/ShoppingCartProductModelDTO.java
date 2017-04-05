package com.lawu.eshop.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车产品型号BO
 * 
 * @author Sunny
 * @date 2017/3/30
 */
public class ShoppingCartProductModelDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	private Long productId;
	
	private String productName;
	
    /**
    * 特征图片
    */
    private String featureImage;
	
	private Long merchantId;
	
	private BigDecimal price;
	
    /**
    * 状态(1-删除2-上架3-下架)
    */
    private Byte status;
	
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
	
	public String getFeatureImage() {
		return featureImage;
	}

	public void setFeatureImage(String featureImage) {
		this.featureImage = featureImage;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
	
}
