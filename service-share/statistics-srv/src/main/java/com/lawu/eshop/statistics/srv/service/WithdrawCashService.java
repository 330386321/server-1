package com.lawu.eshop.statistics.srv.service;

import java.util.List;

import com.lawu.eshop.statistics.param.ReportWithdrawParam;
import com.lawu.eshop.statistics.srv.bo.ReportWithdrawDailyBO;

public interface WithdrawCashService {

	void saveDaily(ReportWithdrawParam param);

	void saveMonth(ReportWithdrawParam param);

	List<ReportWithdrawDailyBO> getDailyList(String reportDate);

	

}
