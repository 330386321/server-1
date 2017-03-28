package com.lawu.eshop.product.query;

import com.lawu.eshop.framework.core.page.PageParam;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * <p>
 * Description: 接收app参数
 * </p>
 * @author Yangqh
 * @date 2017年3月28日 上午10:11:42
 *
 */
public class ProductQuery extends PageParam{
	
	@ApiModelProperty(value = "商品名称", required = false)
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
