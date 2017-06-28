package com.lawu.eshop.jobs.service;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value= "statistics-srv")
public interface WithdrawCashReportService {

	/**
	 * 定时采集数据
	 * 
	 * @author yangqh
	 * @date 2017年6月28日 下午3:37:05
	 */
	void executeCollectDailyData();

	/**
	 * 定时采集数据
	 * 
	 * @author yangqh
	 * @date 2017年6月28日 下午6:07:01
	 */
	void executeCollectMonthData();

	

}
