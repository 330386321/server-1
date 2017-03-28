package com.lawu.eshop.product.query;

import com.lawu.eshop.product.constant.ProductStatusEnum;

public class ProductDataQuery extends ProductQuery{
	
	private Long merchantId;
	
	private ProductStatusEnum status;
	
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public ProductStatusEnum getStatus() {
		return status;
	}
	public void setStatus(ProductStatusEnum status) {
		this.status = status;
	}
	
	
}
