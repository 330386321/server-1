package com.lawu.eshop.statistics.srv.service;

import java.util.List;

import com.lawu.eshop.statistics.dto.ReportCommonBackDTO;
import com.lawu.eshop.statistics.param.ReportKCommonParam;
import com.lawu.eshop.statistics.srv.bo.PointConsumeDailyBO;

public interface PointConsumeService {

	void saveDaily(ReportKCommonParam param);

	void saveMonth(ReportKCommonParam param);

	List<PointConsumeDailyBO> getDailyList(String reportDate);

	void deleteDailyByReportDate(String reportDate);

	void deleteMonthByReportDate(String reportDate);

	ReportCommonBackDTO selectReport(String bdate, String edate);

	

}
