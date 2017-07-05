package com.lawu.eshop.jobs.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.dto.ReportAdEarningsDTO;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.jobs.service.AdSrvService;
import com.lawu.eshop.jobs.service.PropertySrvService;
import com.lawu.eshop.jobs.service.ReportEarningExtendService;
import com.lawu.eshop.jobs.service.ReportEarningService;
import com.lawu.eshop.property.dto.ReportEarningsDTO;
import com.lawu.eshop.statistics.dto.ReportEarningsDailyDTO;
import com.lawu.eshop.statistics.param.ReportEarningParam;
import com.lawu.eshop.utils.DateUtil;

@Service
public class ReportEarningServiceImpl extends BaseController implements ReportEarningExtendService {
	
	private static Logger logger = LoggerFactory.getLogger(ReportEarningServiceImpl.class);
	
	
	@Autowired 
	private AdSrvService  adSrvService;
	
	@Autowired 
	private ReportEarningService reportEarningService;
	
	@Autowired 
	private PropertySrvService propertySrvService;
	

	@Override
	public void executeCollectDailyData() {
		
		 ReportEarningParam param=new ReportEarningParam();
		
		 Result<List<ReportAdEarningsDTO>> adPointResult=adSrvService.getReportEarnings();
		 
		 if(isSuccess(adPointResult)){
			 List<ReportAdEarningsDTO> list= adPointResult.getModel();
			 BigDecimal adTotlePoint=new BigDecimal("0");
			 BigDecimal userTotlePoint=new BigDecimal("0");
			 BigDecimal loveTotlePoint=new BigDecimal("0");
			 for (ReportAdEarningsDTO reportEarningsDTO : list) {
				 
				 adTotlePoint=adTotlePoint.add(reportEarningsDTO.getAdPoint());
				 
				 Result<ReportEarningsDTO> result=propertySrvService.getReportEarnings(reportEarningsDTO.getId());
				
				 ReportEarningsDTO  dto=result.getModel();
				 
				 userTotlePoint=userTotlePoint.add(dto.getUserPoint());
				 loveTotlePoint=loveTotlePoint.add(dto.getLovaPoint());
				
			 }
			 String today = DateUtil.getDateFormat(DateUtil.getDayBefore(new Date()),"yyyy-MM-dd");
			 param.setGmtReport(DateUtil.formatDate(today, "yyyy-MM-dd"));
			 param.setAdPoint(adTotlePoint);
			 param.setLovePoint(loveTotlePoint);
			 param.setUserPoint(userTotlePoint);
			 param.setPlatformPoint(adTotlePoint.subtract(loveTotlePoint.add(userTotlePoint)));
		 }
		 
		 reportEarningService.saveDaily(param);
		
	}

	@Override
	public void executeCollectMonthData() {
		
		String month = DateUtil.getDateFormat(DateUtil.getMonthBefore(new Date()),"yyyy-MM");
		
		 Result<List<ReportEarningsDailyDTO>>  result=reportEarningService.getDailyList(month);
		 
		if (ResultCode.SUCCESS != result.getRet()) {
			logger.error("广告收益报表统计定时采集数据异常：{}", result.getMsg());
			return;
		}

		List<ReportEarningsDailyDTO> list=result.getModel();
		if (list.isEmpty()) {
			logger.info("广告收益报表统计(按日)定时采集数据srv返回空！");
		}
		
		BigDecimal adTotlePoint = new BigDecimal("0");
		BigDecimal userTotlePoint = new BigDecimal("0");
		BigDecimal loveTotlePoint = new BigDecimal("0");
		BigDecimal platformTotlePoint = new BigDecimal("0");
		
		for (ReportEarningsDailyDTO reportEarningsDailyDTO : list) {
			adTotlePoint.add(reportEarningsDailyDTO.getAdPoint());
			userTotlePoint.add(reportEarningsDailyDTO.getUserPoint());
			loveTotlePoint.add(reportEarningsDailyDTO.getLovePoint());
			platformTotlePoint.add(reportEarningsDailyDTO.getPlatformPoint());
		}
		
		ReportEarningParam param = new ReportEarningParam();
		param.setAdPoint(adTotlePoint);
		param.setLovePoint(loveTotlePoint);
		param.setUserPoint(userTotlePoint);
		param.setPlatformPoint(platformTotlePoint);
		param.setGmtReport(DateUtil.formatDate(month+"-01", "yyyy-MM-dd"));
		
		reportEarningService.saveMonth(param);
	}

}
