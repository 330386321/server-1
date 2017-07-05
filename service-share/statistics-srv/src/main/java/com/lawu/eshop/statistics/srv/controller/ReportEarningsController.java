package com.lawu.eshop.statistics.srv.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.statistics.dto.ReportEarningsDailyDTO;
import com.lawu.eshop.statistics.param.ReportEarningParam;
import com.lawu.eshop.statistics.srv.bo.ReportEarningsDailyBO;
import com.lawu.eshop.statistics.srv.service.ReportEarningService;

@RestController
@RequestMapping(value = "reportEarning/")
public class ReportEarningsController extends BaseController{
	
	@Autowired
	private ReportEarningService reportEarningService;
	
	/**
	 * 统计日
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "saveDaily", method = RequestMethod.POST)
	public Result saveDaily(@RequestBody @Valid ReportEarningParam param, BindingResult result) {
		String message = validate(result);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
    	reportEarningService.saveDaily(param);
		return successCreated(ResultCode.SUCCESS);
	}
	
	/**
	 * 统计月
	 * @param param
	 * @param result
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "saveMonth", method = RequestMethod.POST)
	public Result saveMonth(@RequestBody @Valid ReportEarningParam param, BindingResult result) {
		String message = validate(result);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
    	reportEarningService.saveMonth(param);
		return successCreated(ResultCode.SUCCESS);
	}
	
	@RequestMapping(value = "getDailyList", method = RequestMethod.GET)
	public Result<List<ReportEarningsDailyDTO>> getDailyList(@RequestParam("reportDate") String reportDate) {
		List<ReportEarningsDailyBO> rntList = reportEarningService.getDailyList(reportDate);
		List<ReportEarningsDailyDTO> dtoList = new ArrayList<>();
		for(ReportEarningsDailyBO rdo : rntList){
			ReportEarningsDailyDTO dto = new ReportEarningsDailyDTO();
			dto.setGmtCreate(rdo.getGmtCreate());
			dto.setGmtReport(rdo.getGmtReport());
			dto.setId(rdo.getId());
			dto.setAdPoint(rdo.getAdPoint());
			dto.setLovePoint(rdo.getLovePoint());
			dto.setPlatformPoint(rdo.getPlatformPoint());
			dto.setUserPoint(rdo.getUserPoint());
			dtoList.add(dto);
		}
		return successCreated(dtoList);
	}

}
