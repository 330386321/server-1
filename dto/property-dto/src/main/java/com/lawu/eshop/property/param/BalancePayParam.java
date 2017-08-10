package com.lawu.eshop.property.param;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiParam;

/**
 * 
 * <p>
 * Description: 余额支付参数对象
 * </p>
 * 
 * @author Yangqh
 * @date 2017年4月11日 下午5:35:25
 *
 */
public class BalancePayParam {

	// 业务表ID(支持多个,用英文逗号分割)
	@NotBlank(message = "bizIds不能为空")
	@ApiParam (name="bizIds",required = true, value = "业务表ID(支持多个,用英文逗号分割)")
	private String bizIds;

	@ApiParam(name = "regionPaths", required = false, value = "商家省市区ID(格式：省ID/市ID/区ID，用于代理商系统区域统计)")
	private String regionPaths;

	public String getBizIds() {
		return bizIds;
	}

	public void setBizIds(String bizIds) {
		this.bizIds = bizIds;
	}

	public String getRegionPaths() {
		return regionPaths;
	}

	public void setRegionPaths(String regionPaths) {
		this.regionPaths = regionPaths;
	}
}