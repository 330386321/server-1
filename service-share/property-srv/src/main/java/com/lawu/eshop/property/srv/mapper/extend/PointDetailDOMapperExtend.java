package com.lawu.eshop.property.srv.mapper.extend;

import java.util.List;

import com.lawu.eshop.property.srv.domain.extend.ReportAdEarningsPointView;
import com.lawu.eshop.property.srv.domain.extend.ReportAdPointGroupByAreaView;

public interface PointDetailDOMapperExtend {
	
	ReportAdEarningsPointView  getReportAdEarningsPoint(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView  getReportAdEarningsLovePoint(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView getUserPointByBzId(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView getLovePointByBzId(ReportAdEarningsPointView view);
    
	List<ReportAdPointGroupByAreaView> getReportAdPointGroupByArea(String bdate, String edate);   
}