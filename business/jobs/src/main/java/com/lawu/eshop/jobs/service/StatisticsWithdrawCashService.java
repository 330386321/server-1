package com.lawu.eshop.jobs.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.ReportWithdrawDailyDTO;
import com.lawu.eshop.statistics.param.ReportKCommonParam;

@FeignClient(value= "statistics-srv")
public interface StatisticsWithdrawCashService {

	//-----------------------------提现统计
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "withdrawCash/saveDaily")
	Result saveDaily(@RequestBody ReportKCommonParam reportWithdraw);

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "withdrawCash/saveMonth")
	Result saveMonth(@RequestBody ReportKCommonParam reportWithdraw);
	
	@RequestMapping(method = RequestMethod.GET, value = "withdrawCash/getDailyList")
	Result<List<ReportWithdrawDailyDTO>> getDailyList(@RequestParam("reportDate") String reportDate);

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.DELETE, value = "withdrawCash/deleteDailyByReportDate")
	Result deleteDailyByReportDate(@RequestParam("reportDate") String reportDate);
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.DELETE, value = "withdrawCash/deleteMonthByReportDate")
	Result deleteMonthByReportDate(@RequestParam("reportDate") String reportDate);	
	
}