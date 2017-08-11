package com.lawu.eshop.jobs.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.ReportAreaAdPointDailyDTO;
import com.lawu.eshop.statistics.dto.ReportAreaAdPointMonthDTO;
import com.lawu.eshop.statistics.param.ReportAreaAdPointDailyParams;
import com.lawu.eshop.statistics.param.ReportAreaAdPointMonthParams;


@FeignClient(value= "statistics-srv")
public interface StatisticsSrvService {

	
	@RequestMapping(method = RequestMethod.POST, value = "reportAreaAdPointDaily/selectReportAreaAdPointDaily/{areaId}")
	Result<List<ReportAreaAdPointDailyDTO>> selectReportAreaAdPointDaily(@PathVariable("areaId") Integer areaId, @RequestParam("date") String date);	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "reportAreaAdPointDaily/insertReportAreaAdPointDaily", method = RequestMethod.POST)
	Result insertReportAreaAdPointDaily(@RequestBody ReportAreaAdPointDailyParams param);
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "reportAreaAdPointDaily/deleteReportAreaAdPointDaily", method = RequestMethod.POST)
	Result deleteReportAreaAdPointDaily(@RequestParam(value = "id") Long id);
	
	@RequestMapping(value = "reportAreaAdPointDaily/selectReportAreaAdPointDailyInMonth", method = RequestMethod.GET)
	Result<List<ReportAreaAdPointMonthDTO>> selectReportAreaAdPointDailyInMonth(@RequestParam(value = "bdate") String bdate, 
			@RequestParam(value = "edate") String edate);
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "reportAreaAdPointMonth/insertReportAreaAdPointMonth", method = RequestMethod.POST)
	Result insertReportAreaAdPointMonth(@RequestBody ReportAreaAdPointMonthParams param);
}
