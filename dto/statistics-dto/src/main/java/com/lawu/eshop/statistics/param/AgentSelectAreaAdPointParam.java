package com.lawu.eshop.statistics.param;

import java.util.Date;

import io.swagger.annotations.ApiParam;

public class AgentSelectAreaAdPointParam {

	@ApiParam (value = "经度")
	private Integer cityId;
	
	@ApiParam (value = "开始时间")
	private Date bdate;
	
	@ApiParam (value = "结束时间")
	private Date edate;

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	
}
