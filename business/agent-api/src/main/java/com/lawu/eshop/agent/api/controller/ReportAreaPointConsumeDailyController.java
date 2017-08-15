package com.lawu.eshop.agent.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.agent.api.service.ReportAreaPointConsumeDailyService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.ReportAreaPointConsumeDailyDTO;
import com.lawu.eshop.statistics.dto.ReportAreaPointConsumeDailyForCircleDTO;

import io.swagger.annotations.Api;

/**
 * @author hongqm
 * @date 2017/8/15
 */
@Api(tags = "reportAreaPointConsumeDaily")
@RestController
@RequestMapping(value = "reportAreaPointConsumeDaily/")
public class ReportAreaPointConsumeDailyController extends BaseController{
	
	@Autowired
	private ReportAreaPointConsumeDailyService reportAreaPointConsumeDailyService;
	
	/**
	 * 区域积分消费日查询
	 * @param cityId
	 * @param bdate
	 * @param edate
	 * @return
	 */
	@RequestMapping(value = "getReportAreaPointConsumeDaily", method = RequestMethod.GET)
	public Result<ReportAreaPointConsumeDailyForCircleDTO> getReportAreaPointConsumeDaily(@RequestParam("cityId")Integer cityId, @RequestParam("bdate")String bdate, @RequestParam("edate")String edate){
		Result<List<ReportAreaPointConsumeDailyDTO>> result = reportAreaPointConsumeDailyService.getReportAreaPointConsumeDaily(cityId, bdate, edate);
		ReportAreaPointConsumeDailyForCircleDTO dto = new ReportAreaPointConsumeDailyForCircleDTO();
		BigDecimal memberPoint = new BigDecimal(0);
		BigDecimal memberRechargePoint = new BigDecimal(0);
		BigDecimal merchantPoint = new BigDecimal(0);
		BigDecimal merchantRechargePoint = new BigDecimal(0);
		BigDecimal totalPoint = new BigDecimal(0);
		BigDecimal totalRechargePoint = new BigDecimal(0);
		if(result.getModel() != null && !result.getModel().isEmpty()) {
			for(ReportAreaPointConsumeDailyDTO d : result.getModel()) {
				memberPoint.add(d.getMemberPoint());
				memberRechargePoint.add(d.getMemberRechargePoint());
				merchantPoint.add(d.getMerchantPoint());
				merchantRechargePoint.add(d.getMemberRechargePoint());
				totalRechargePoint.add(d.getTotalRechargePoint());
				totalPoint.add(d.getTotalPoint());
			}
		}
		return successGet(dto);
	}
}
