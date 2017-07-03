package com.lawu.eshop.operator.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.ReportAdEarningsDTO;
import com.lawu.eshop.statistics.param.ReportAdEarningsQueryParam;

@FeignClient(value = "statistics-srv")
public interface ReportAdEarningsService {
	
	/**
	 * 广告收益统计列表
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "reportAdEarnings/selectReportAdEarnings", method = RequestMethod.GET)
	public Result<Page<ReportAdEarningsDTO>> selectReportAdEarnings(@RequestBody ReportAdEarningsQueryParam query);

}
