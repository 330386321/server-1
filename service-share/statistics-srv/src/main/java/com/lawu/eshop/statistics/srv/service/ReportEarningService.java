package com.lawu.eshop.statistics.srv.service;

import java.util.List;

import com.lawu.eshop.statistics.param.ReportEarningParam;
import com.lawu.eshop.statistics.srv.bo.ReportEarningsDailyBO;

public interface ReportEarningService {

	void saveDaily(ReportEarningParam param);

	void saveMonth(ReportEarningParam param);
	
	List<ReportEarningsDailyBO> getDailyList(String reportDate);
}
