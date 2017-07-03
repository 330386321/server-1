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
import com.lawu.eshop.statistics.dto.PointConsumeDailyDTO;
import com.lawu.eshop.statistics.dto.ReportCommonBackDTO;
import com.lawu.eshop.statistics.param.ReportKCommonParam;
import com.lawu.eshop.statistics.srv.bo.PointConsumeDailyBO;
import com.lawu.eshop.statistics.srv.service.PointConsumeService;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年6月30日 上午11:16:42
 *
 */
@RestController
@RequestMapping(value = "pointConsume/")
public class PointConsumeController extends BaseController {

	@Autowired
	private PointConsumeService pointConsumeService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "saveDaily", method = RequestMethod.POST)
	public Result saveDaily(@RequestBody @Valid ReportKCommonParam param, BindingResult result) {
		String message = validate(result);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
    	pointConsumeService.saveDaily(param);
		return successCreated(ResultCode.SUCCESS);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "saveMonth", method = RequestMethod.POST)
	public Result saveMonth(@RequestBody @Valid ReportKCommonParam param, BindingResult result) {
		String message = validate(result);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
    	pointConsumeService.saveMonth(param);
		return successCreated(ResultCode.SUCCESS);
	}
	
	@RequestMapping(value = "getDailyList", method = RequestMethod.GET)
	public Result<List<PointConsumeDailyDTO>> getDailyList(@RequestParam("reportDate") String reportDate) {
		List<PointConsumeDailyBO> rntList = pointConsumeService.getDailyList(reportDate);
		List<PointConsumeDailyDTO> dtoList = new ArrayList<>();
		for(PointConsumeDailyBO rdo : rntList){
			PointConsumeDailyDTO dto = new PointConsumeDailyDTO();
			dto.setGmtCreate(rdo.getGmtCreate());
			dto.setGmtReport(rdo.getGmtReport());
			dto.setId(rdo.getId());
			dto.setMemberPoint(rdo.getMemberPoint());
			dto.setMerchantPoint(rdo.getMerchantPoint());
			dto.setTotalPoint(rdo.getTotalPoint());
			dtoList.add(dto);
		}
		return successCreated(dtoList);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteDailyByReportDate", method = RequestMethod.DELETE)
	public Result deleteDailyByReportDate(@RequestParam("reportDate") String reportDate) {
		pointConsumeService.deleteDailyByReportDate(reportDate);
		return successCreated(ResultCode.SUCCESS);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteMonthByReportDate", method = RequestMethod.DELETE)
	public Result deleteMonthByReportDate(@RequestParam("reportDate") String reportDate) {
		pointConsumeService.deleteMonthByReportDate(reportDate);
		return successCreated(ResultCode.SUCCESS);
	}
	
	/**
	 * 
	 * @param date
	 * @param isTotal
	 * @return
	 * @author yangqh
	 * @date 2017年7月3日 下午3:40:52
	 */
	@RequestMapping(value = "selectReport", method = RequestMethod.GET)
	public ReportCommonBackDTO selectReport(@RequestParam("date") String date,@RequestParam("isTotal") String isTotal) {
		return pointConsumeService.selectReport(date,isTotal);
	}
}
