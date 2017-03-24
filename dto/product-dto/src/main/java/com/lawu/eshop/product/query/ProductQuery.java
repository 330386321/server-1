package com.lawu.eshop.product.query;

import com.lawu.eshop.framework.core.page.PageParam;

import io.swagger.annotations.ApiModelProperty;

public class ProductQuery extends PageParam{
	
//	@ApiModelProperty(value = "商家ID", required = true)
	private Long merchantId;
	
//	@ApiModelProperty(value = "商品名称", required = false)
	private String name;
	
//	@ApiModelProperty(value = "状态", required = true)
	private Integer status;
	
//	@ApiModelProperty(value = "当前第几页", required = true)
	private Integer pageIndex;
	
//	@ApiModelProperty(value = "每页展示数量", required = true)
	private Integer pageSize;
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
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
