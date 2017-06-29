package com.lawu.eshop.jobs.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.ReportWithdrawDailyDTO;
import com.lawu.eshop.statistics.param.ReportWithdrawParam;

@FeignClient(value= "statistics-srv")
public interface StatisticsWithdrawCashService {

	/**
	 * 
	 * @param reportWithdraw
	 * @return
	 * @author yangqh
	 * @date 2017年6月28日 下午5:55:45
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "withdrawCash/saveDaily")
	Result saveDaily(@RequestBody ReportWithdrawParam reportWithdraw);

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "withdrawCash/saveMonth")
	Result saveMonth(@RequestBody ReportWithdrawParam reportWithdraw);
	
	@RequestMapping(method = RequestMethod.GET, value = "withdrawCash/getDailyList")
	Result<List<ReportWithdrawDailyDTO>> getDailyList(@RequestParam("reportDate") String reportDate);
}
