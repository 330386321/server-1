package com.lawu.eshop.product.param;

import java.util.List;

import io.swagger.annotations.ApiParam;

public class JoinSeckillActivityParam {
	
	@ApiParam (name="seckillActivityId",required = true, value = "活动id")
	private Long seckillActivityId;
	
	@ApiParam (name="productId",required = true, value = "商品Id")
	private Long productId;
	
	@ApiParam (name="modelList",required = true)
	private List<ModelParam> modelList;

	public Long getSeckillActivityId() {
		return seckillActivityId;
	}

	public void setSeckillActivityId(Long seckillActivityId) {
		this.seckillActivityId = seckillActivityId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public List<ModelParam> getModelList() {
		return modelList;
	}

	public void setModelList(List<ModelParam> modelList) {
		this.modelList = modelList;
	}
	
	

}
