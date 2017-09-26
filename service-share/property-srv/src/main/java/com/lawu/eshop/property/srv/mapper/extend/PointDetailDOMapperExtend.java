package com.lawu.eshop.property.srv.mapper.extend;

import java.util.List;

import com.lawu.eshop.property.srv.domain.extend.IncomeMsgDOView;
import com.lawu.eshop.property.srv.domain.extend.IncomeMsgExample;
import org.apache.ibatis.annotations.Param;

import com.lawu.eshop.property.srv.domain.extend.AreaPointConsumeDOView;
import com.lawu.eshop.property.srv.domain.extend.ReportAdEarningsPointView;
import com.lawu.eshop.property.srv.domain.extend.ReportAdPointGroupByAreaView;

public interface PointDetailDOMapperExtend {
	
	ReportAdEarningsPointView  getReportAdEarningsPoint(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView  getReportAdEarningsLovePoint(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView getUserPointByBzId(ReportAdEarningsPointView view);
	
	ReportAdEarningsPointView getLovePointByBzId(ReportAdEarningsPointView view);
    
	List<ReportAdPointGroupByAreaView> getReportAdPointGroupByArea(@Param("bdate") String bdate, @Param("edate")String edate);
	
	List<AreaPointConsumeDOView> getAreaPointConsume(@Param("bdate") String bdate, @Param("edate")String edate);
	
	List<AreaPointConsumeDOView> getAreaPointRefund(@Param("bdate") String bdate, @Param("edate")String edate);

    List<IncomeMsgDOView> getIncomeMsgDataList(IncomeMsgExample example);
}