package com.lawu.eshop.operator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.ReportWithdrawCashService;
import com.lawu.eshop.statistics.dto.ReportCommonBackDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年6月30日 下午5:20:16
 *
 */
@Api(tags = "reportWithdrawCash")
@RestController
@RequestMapping(value = "reportWithdrawCash/")
public class ReportWithdrawCashController extends BaseController {

	@Autowired
	private ReportWithdrawCashService reportWithdrawCashService;

	@ApiOperation(value = "提现日统计", notes = "提现日统计,[]（杨清华）", httpMethod = "GET")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	//@RequiresPermissions("reportWithdrawCash:list")
	public Result<ReportCommonBackDTO> selectReport(@RequestParam("flag") String flag,@RequestParam("date") String date,@RequestParam("isTotal") String isTotal) {
		
//		List<Integer> xAxisData = new ArrayList<>();
//		xAxisData.add(1);
//		xAxisData.add(2);
//		xAxisData.add(3);
//		List<BigDecimal> yAxisMemberData = new ArrayList<>();
//		yAxisMemberData.add(new BigDecimal("2"));
//		yAxisMemberData.add(new BigDecimal("3"));
//		yAxisMemberData.add(new BigDecimal("4"));
//		List<BigDecimal> yAxisMerchantData = new ArrayList<>();
//		yAxisMerchantData.add(new BigDecimal("1"));
//		yAxisMerchantData.add(new BigDecimal("4"));
//		yAxisMerchantData.add(new BigDecimal("2"));
//		ReportCommonBackDTO dto = new ReportCommonBackDTO();
//		dto.setxAxisData(xAxisData);
//		dto.setyAxisMemberData(yAxisMemberData);
//		dto.setyAxisMerchantData(yAxisMerchantData);
		
		return successCreated(reportWithdrawCashService.selectReport(flag,date,isTotal));
	}

	
}
