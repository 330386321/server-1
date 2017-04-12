package com.lawu.eshop.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.lawu.eshop.framework.core.page.Page;

import io.swagger.annotations.ApiModelProperty;

public class PointDetailPageDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户积分
	 */
	@ApiModelProperty(value = "用户积分", required = true)
	private BigDecimal point;
	
	/**
	 * 积分明细分页数据
	 */
	@ApiModelProperty(value = "积分明细分页数据", required = true)
	private Page<PointDetailDTO> page;

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		this.point = point;
	}

	public Page<PointDetailDTO> getPage() {
		return page;
	}

	public void setPage(Page<PointDetailDTO> page) {
		this.page = page;
	}
	
}