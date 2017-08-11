package com.lawu.eshop.statistics.srv.mapper.extend;

import java.util.Date;
import java.util.List;

import com.lawu.eshop.statistics.srv.domain.extend.ReportAreaAdPointDailyInMonthDOView;

public interface ReportAreaAdPointDailyDOMapperExtend {

	List<ReportAreaAdPointDailyInMonthDOView> selectReportAreaAdPointDailyInMonth(Date bdate,Date edate);

}
