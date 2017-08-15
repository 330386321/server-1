package com.lawu.eshop.agent.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.ReportAreaVolumnMonthDTO;
/**
 * @author hongqm
 * @date 2017/8/14
 */
@FeignClient(value = "statistics-srv")
public interface ReportAreaVolumnMonthService {

	
	@RequestMapping(value = "reportAreaVolumnMonth/selectReportAreaVolumnMonth", method = RequestMethod.GET)
	Result<List<ReportAreaVolumnMonthDTO>> selectReportAreaVolumnMonth(@RequestParam("cityId")Integer cityId,@RequestParam("bdate") String bdate,@RequestParam("edate") String edate);
	
}
