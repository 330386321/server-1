package com.lawu.eshop.property.srv.mapper.extend;

import java.util.List;

import com.lawu.eshop.property.srv.domain.extend.ReportAdEarningsPointView;
import com.lawu.eshop.property.srv.domain.extend.ReportAdPointGroupByAreaView;

import org.apache.ibatis.annotations.Param;

public interface PointDetailDOMapperExtend {
	
	ReportAdEarningsPointView  getReportAdEarningsPoint(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView  getReportAdEarningsLovePoint(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView getUserPointByBzId(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView getLovePointByBzId(ReportAdEarningsPointView view);
    
	List<ReportAdPointGroupByAreaView> getReportAdPointGroupByArea(@Param("bdate") String bdate, @Param("edate")String edate);   
}