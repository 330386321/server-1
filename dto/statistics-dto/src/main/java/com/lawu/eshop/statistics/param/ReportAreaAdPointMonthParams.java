package com.lawu.eshop.statistics.param;

import java.math.BigDecimal;
import java.util.Date;

public class ReportAreaAdPointMonthParams {

	private BigDecimal reportTotalPoint;
	
	private Integer provinceId;
	
	private Integer cityId;
	
	private Integer areaId;
	
	private Date gmtReport;
	
	private Date gmtCreate;

	public BigDecimal getReportTotalPoint() {
		return reportTotalPoint;
	}

	public void setReportTotalPoint(BigDecimal reportTotalPoint) {
		this.reportTotalPoint = reportTotalPoint;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Date getGmtReport() {
		return gmtReport;
	}

	public void setGmtReport(Date gmtReport) {
		this.gmtReport = gmtReport;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
}
