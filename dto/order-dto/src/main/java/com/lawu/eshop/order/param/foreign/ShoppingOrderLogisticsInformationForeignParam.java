package com.lawu.eshop.order.param.foreign;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * 商家填写物流信息
 * api暴露给app参数
 * 
 * @author Sunny
 * @date 2017/4/11
 */
public class ShoppingOrderLogisticsInformationForeignParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 物流编号
	 */
	@ApiModelProperty(required = false, value = "物流编号")
	private String waybillNum;

	/**
	 * 快递公司id
	 */
	@ApiModelProperty(required = false, value = "快递公司id")
	private Integer expressCompanyId;
	
	/**
	 * 是否需要物流
	 */
	@NotNull(message = "是否需要物流不能为空")
	@ApiModelProperty(required = true, value = "是否需要物流")
	private Boolean isNeedsLogistics;


	public String getWaybillNum() {
		return waybillNum;
	}

	public void setWaybillNum(String waybillNum) {
		this.waybillNum = waybillNum;
	}

	public Integer getExpressCompanyId() {
		return expressCompanyId;
	}

	public void setExpressCompanyId(Integer expressCompanyId) {
		this.expressCompanyId = expressCompanyId;
	}

	public Boolean getIsNeedsLogistics() {
		return isNeedsLogistics;
	}

	public void setIsNeedsLogistics(Boolean isNeedsLogistics) {
		this.isNeedsLogistics = isNeedsLogistics;
	}

}
