package com.lawu.eshop.statistics.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.ReportUserIncomeExpenditureDTO;
import com.lawu.eshop.statistics.param.ReportUserIncomeExpenditureQueryParam;
import com.lawu.eshop.statistics.param.ReportUserIncomeExpenditureSaveParam;
import com.lawu.eshop.statistics.srv.bo.ReportUserIncomeExpenditureBO;
import com.lawu.eshop.statistics.srv.converter.ReportUserIncomeExpenditureConverter;
import com.lawu.eshop.statistics.srv.service.ReportUserIncomeExpenditureService;

/**
 * 平台总销量接口提供类
 * 
 * @author Sunny
 * @date 2017年7月3日
 */
@RestController
@RequestMapping(value = "reportUserIncomeExpenditure/")
public class ReportUserIncomeExpenditureController extends BaseController {

	@Autowired
	private ReportUserIncomeExpenditureService reportUserIncomeExpenditureService;
	
	/**
	 * 保存平台总销量记录
	 * 
	 * @param param
	 * @author Sunny
	 * @date 2017年7月3日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody ReportUserIncomeExpenditureSaveParam param){
		reportUserIncomeExpenditureService.save(param);
    	return successCreated();
    }
    
    /**
     * 查询平台总销量记录
     * 
     * @param param
     * @return
     * @author Sunny
     * @date 2017年7月3日
     */
	@RequestMapping(value = "page", method = RequestMethod.PUT)
    public Result<Page<ReportUserIncomeExpenditureDTO>> list(@RequestBody ReportUserIncomeExpenditureQueryParam param){
		Page<ReportUserIncomeExpenditureBO> reportSalesBOPage = reportUserIncomeExpenditureService.page(param);
		Page<ReportUserIncomeExpenditureDTO> rtn = ReportUserIncomeExpenditureConverter.convert(reportSalesBOPage);
    	return successGet(rtn);
    }
}
