package com.lawu.eshop.statistics.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawu.eshop.statistics.constants.ReportTypeEnum;

/**
 * 平台总销量查询参数
 * 
 * @author Sunny
 * @date 2017年6月30日
 */
public class PlatformTotalSalesQueryParam {
	
	/**
	 * 报表类型
	 */
	private ReportTypeEnum type;
	
	/**
	 * 查询日期
	 */
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
