package com.lawu.eshop.agent.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.agent.api.service.ReportAreaPointConsumeMonthService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.ReportAreaPointConsumeMonthForCircleDTO;

import io.swagger.annotations.Api;

/**
 * @author hongqm
 * @date 2017/8/15
 */
@Api(tags = "ReportAreaPointConsumeMonth")
@RestController
@RequestMapping(value = "ReportAreaPointConsumeMonth/")
public class ReportAreaPointConsumeMonthController extends BaseController{
	
	@Autowired
	private ReportAreaPointConsumeMonthService reportAreaPointConsumeMonthService;
	
	/**
	 * 区域积分消费月查询
	 * @param cityId
	 * @param bdate
	 * @param edate
	 * @return
	 */
	@RequestMapping(value = "getReportAreaPointConsumeDaily", method = RequestMethod.GET)
	public Result<ReportAreaPointConsumeMonthForCircleDTO> getReportAreaPointConsumeDaily(@RequestParam("cityId")Integer cityId, @RequestParam("bdate")String bdate, @RequestParam("edate")String edate){
		return reportAreaPointConsumeMonthService.selectReportAreaPointConsumeMonth(cityId, bdate + "-1", edate + "-1");
	}
}
