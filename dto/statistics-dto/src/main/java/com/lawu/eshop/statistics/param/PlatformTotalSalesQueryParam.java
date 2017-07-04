package com.lawu.eshop.statistics.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawu.eshop.statistics.constants.ReportTypeEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 平台总销量查询参数
 * 
 * @author Sunny
 * @date 2017年6月30日
 */
@ApiModel
public class PlatformTotalSalesQueryParam {
	
	/**
	 * 报表类型
	 */
	@ApiModelProperty(value = "报表类型", required = true)
	private ReportTypeEnum type;
	
	/**
	 * 查询日期(yyyy-MM-dd)
	 */
	@ApiModelProperty(value = "查询日期", required = true)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	public ReportTypeEnum getType() {
		return type;
	}

	public void setType(ReportTypeEnum type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
