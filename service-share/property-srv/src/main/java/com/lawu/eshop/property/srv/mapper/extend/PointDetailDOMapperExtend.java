package com.lawu.eshop.property.srv.mapper.extend;

import com.lawu.eshop.property.srv.domain.extend.ReportAdEarningsPointView;

public interface PointDetailDOMapperExtend {
	
	ReportAdEarningsPointView  getReportAdEarningsPoint(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView  getReportAdEarningsLovePoint(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView getUserPointByBzId(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView getLovePointByBzId(ReportAdEarningsPointView view);
    
}