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
import com.lawu.eshop.statistics.dto.ReportCommonBackDTO;
import com.lawu.eshop.statistics.dto.ReportWithdrawDailyDTO;
import com.lawu.eshop.statistics.param.ReportKCommonParam;
import com.lawu.eshop.statistics.srv.bo.ReportWithdrawDailyBO;
import com.lawu.eshop.statistics.srv.service.WithdrawCashService;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年6月28日 下午5:42:20
 *
 */
@RestController
@RequestMapping(value = "withdrawCash/")
public class WithdrawCashController extends BaseController {

	@Autowired
	private WithdrawCashService withdrawCashService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "saveDaily", method = RequestMethod.POST)
	public Result saveDaily(@RequestBody @Valid ReportKCommonParam param, BindingResult result) {
		String message = validate(result);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
    	withdrawCashService.saveDaily(param);
		return successCreated(ResultCode.SUCCESS);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "saveMonth", method = RequestMethod.POST)
	public Result saveMonth(@RequestBody @Valid ReportKCommonParam param, BindingResult result) {
		String message = validate(result);
    	if (message != null) {
    		return successCreated(ResultCode.REQUIRED_PARM_EMPTY, message);
    	}
    	withdrawCashService.saveMonth(param);
		return successCreated(ResultCode.SUCCESS);
	}
	
	@RequestMapping(value = "getDailyList", method = RequestMethod.GET)
	public Result<List<ReportWithdrawDailyDTO>> getDailyList(@RequestParam("reportDate") String reportDate) {
		List<ReportWithdrawDailyBO> rntList = withdrawCashService.getDailyList(reportDate);
		List<ReportWithdrawDailyDTO> dtoList = new ArrayList<>();
		for(ReportWithdrawDailyBO rdo : rntList){
			ReportWithdrawDailyDTO dto = new ReportWithdrawDailyDTO();
			dto.setGmtCreate(rdo.getGmtCreate());
			dto.setGmtReport(rdo.getGmtReport());
			dto.setId(rdo.getId());
			dto.setMemberMoney(rdo.getMemberMoney());
			dto.setMerchantMoney(rdo.getMerchantMoney());
			dto.setTotalMoney(rdo.getTotalMoney());
			dtoList.add(dto);
		}
		return successCreated(dtoList);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteDailyByReportDate", method = RequestMethod.DELETE)
	public Result deleteDailyByReportDate(@RequestParam("reportDate") String reportDate) {
		withdrawCashService.deleteDailyByReportDate(reportDate);
		return successCreated(ResultCode.SUCCESS);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "deleteMonthByReportDate", method = RequestMethod.DELETE)
	public Result deleteMonthByReportDate(@RequestParam("reportDate") String reportDate) {
		withdrawCashService.deleteMonthByReportDate(reportDate);
		return successCreated(ResultCode.SUCCESS);
	}
	
	/**
	 * 
	 * @param date 时间条件
	 * @param isTotal 0-区分商家用户1-平台总提现
	 * @return
	 * @author yangqh
	 * @date 2017年7月3日 上午10:28:40
	 */
	@RequestMapping(value = "selectReport", method = RequestMethod.GET)
	public ReportCommonBackDTO selectReport(@RequestParam("date") String date,@RequestParam("isTotal") String isTotal) {
		return withdrawCashService.selectReport(date,isTotal);
	}
}
