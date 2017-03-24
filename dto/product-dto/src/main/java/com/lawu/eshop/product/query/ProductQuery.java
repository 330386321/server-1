package com.lawu.eshop.product.query;

import com.lawu.eshop.framework.core.page.PageParam;

public class ProductQuery extends PageParam{
	
//	@ApiModelProperty(value = "商家ID", required = true)
	private Long merchantId;
	
//	@ApiModelProperty(value = "商品名称", required = false)
	private String name;
	
//	@ApiModelProperty(value = "状态", required = true)
	private Integer status;
	
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
