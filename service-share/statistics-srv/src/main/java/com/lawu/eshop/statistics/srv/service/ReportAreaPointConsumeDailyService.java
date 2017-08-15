package com.lawu.eshop.statistics.srv.service;

import java.util.List;

import com.lawu.eshop.statistics.param.ReportAreaPointConsumeDailyParam;
import com.lawu.eshop.statistics.srv.bo.ReportAreaPointConsumeDailyBO;

public interface ReportAreaPointConsumeDailyService {

	int insertReportAreaPointConsumeDaily(ReportAreaPointConsumeDailyParam param);
	
	List<ReportAreaPointConsumeDailyBO> getReportAreaPointConsumeDaily(Integer cityId, String bdate, String edate);
}
