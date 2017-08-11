package com.lawu.eshop.statistics.dto;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class ReportAreaAdPorintDailyByAreaIdDTO {

	@ApiModelProperty(value = "投放总积分")
	private BigDecimal reportTotalPoint;
	
	@ApiModelProperty(value = "日期")
	private Date gmtReport;

	public BigDecimal getReportTotalPoint() {
		return reportTotalPoint;
	}

	public void setReportTotalPoint(BigDecimal reportTotalPoint) {
		this.reportTotalPoint = reportTotalPoint;
	}

	public Date getGmtReport() {
		return gmtReport;
	}

	public void setGmtReport(Date gmtReport) {
		this.gmtReport = gmtReport;
	}
}
