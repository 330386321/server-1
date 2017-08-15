package com.lawu.eshop.statistics.srv.mapper.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lawu.eshop.statistics.srv.domain.extend.ReportAreaPointConsumeDailyInMonthDOView;

public interface ReportAreaPointConsumeMonthDOMapperExtend {
	
	List<ReportAreaPointConsumeDailyInMonthDOView> selectReportAreaPointConsumeDailyInMonth(@Param("bdate") String bdate, @Param("edate") String edate);

}
