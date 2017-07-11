package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.property.param.ReportAdEarningsPointParam;
import com.lawu.eshop.property.srv.bo.ReportAdEarningsPointBO;
import com.lawu.eshop.property.srv.bo.ReportEarningsBO;

public interface ReportAdEarningsPointService {
	
	ReportAdEarningsPointBO  getReportAdEarningsPoint(ReportAdEarningsPointParam ReportAdEarningsPointParam);
	
	
	ReportEarningsBO getReportEarnings(Long bzId);

}
