package com.lawu.eshop.statistics.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.statistics.param.ReportAreaAdPointMonthParams;
import com.lawu.eshop.statistics.srv.service.ReportAreaAdPointMonthService;

@RestController
@RequestMapping(value = "reportAreaAdPointMonth/")
public class ReportAreaAdPointMonthController extends BaseController {

	@Autowired
	private ReportAreaAdPointMonthService reportAreaAdPointMonthService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "insertReportAreaAdPointMonth", method = RequestMethod.POST)
	public Result insertReportAreaAdPointMonth(@RequestBody ReportAreaAdPointMonthParams param) {
		reportAreaAdPointMonthService.insertReportAreaAdPointMonth(param);
		return successCreated(ResultCode.SUCCESS);
	}
}
