package com.lawu.eshop.jobs.impl.ad;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.ad.dto.AdReportDTO;
import com.lawu.eshop.ad.dto.ReportAdEarningsDTO;
import com.lawu.eshop.ad.param.AdReportParam;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.jobs.service.AdSrvService;
import com.lawu.eshop.jobs.service.PropertySrvService;
import com.lawu.eshop.jobs.service.ReportEarningService;
import com.lawu.eshop.property.dto.ReportEarningsDTO;
import com.lawu.eshop.statistics.param.ReportEarningParam;
import com.lawu.eshop.utils.DateUtil;
import com.lawu.jobsextend.AbstractPageResultJob;


public class ReportEarningDailyReportJob extends AbstractPageResultJob<ReportAdEarningsDTO,AdReportDTO> {

    @Autowired
	private AdSrvService adSrvService;

	@Autowired
	private ReportEarningService reportEarningService;

	@Autowired
	private PropertySrvService propertySrvService;
    

	@Override
	public AdReportDTO executePage(List<ReportAdEarningsDTO> list) {
		BigDecimal adTotlePoint = new BigDecimal("0");
		BigDecimal userTotlePoint = new BigDecimal("0");
		BigDecimal loveTotlePoint = new BigDecimal("0");
		
		List<Long> bizIds = new ArrayList<>();
		for (ReportAdEarningsDTO reportEarningsDTO : list) {
			if(reportEarningsDTO.getAdPoint()!=null){
				bizIds.add(reportEarningsDTO.getId());
				adTotlePoint = adTotlePoint.add(reportEarningsDTO.getAdPoint());
			}
		
		}
		AdReportDTO adReport = new AdReportDTO();
		if(!bizIds.isEmpty()){
			Result<ReportEarningsDTO> result = propertySrvService.getReportEarnings(bizIds);
			adTotlePoint=result.getModel().getLovaPoint();
			userTotlePoint=result.getModel().getUserPoint();
		}
		
		adReport.setAdTotlePoint(adTotlePoint);
		adReport.setLoveTotlePoint(loveTotlePoint);
		adReport.setUserTotlePoint(userTotlePoint);
		
		return adReport;
	}

	@Override
	public void executeSummary(List<AdReportDTO> list) {
		BigDecimal adTotlePoint = new BigDecimal("0");
		BigDecimal userTotlePoint = new BigDecimal("0");
		BigDecimal loveTotlePoint = new BigDecimal("0");
		for (AdReportDTO adReportDTO : list) {
			adTotlePoint=adTotlePoint.add(adReportDTO.getAdTotlePoint());
			userTotlePoint=userTotlePoint.add(adReportDTO.getUserTotlePoint());
			loveTotlePoint=loveTotlePoint.add(adReportDTO.getLoveTotlePoint());
		}
		ReportEarningParam param = new ReportEarningParam();
		param.setGmtReport(DateUtil.formatDate(DateUtil.getDateFormat(DateUtil.getDayBefore(new Date()), "yyyy-MM-dd"), "yyyy-MM-dd"));
		param.setAdPoint(adTotlePoint);
		param.setLovePoint(loveTotlePoint);
		param.setUserPoint(userTotlePoint);
		param.setPlatformPoint(adTotlePoint.subtract(loveTotlePoint.add(userTotlePoint)));
		reportEarningService.saveDaily(param);
	}

	@Override
	public List<ReportAdEarningsDTO> queryPage(int offset, int pageSize) {
		Date lastDay = reportEarningService.getDaily();
		List<ReportAdEarningsDTO> list = new ArrayList<>();
		if (lastDay != null && !DateUtils.isSameDay(lastDay, DateUtil.getSomeDay(new Date(), -1))) {
			int betweenDay = DateUtil.daysOfTwo(lastDay);
			/*for (int i = 1; i < betweenDay; i++) {
				String today = DateUtil.getDateFormat(DateUtil.getSomeDay(lastDay, i), "yyyy-MM-dd");
				AdReportParam param = new AdReportParam();
				param.setToday(today);
				param.setCurrentPage(currentPage);
				param.setPageSize(pageSize);
				Result<List<ReportAdEarningsDTO>> adPointResult = adSrvService.getReportEarnings(param);
				list = adPointResult.getModel();
				executePage(list);
			}*/
		} else {
			String today = DateUtil.getDateFormat(DateUtil.getDayBefore(new Date()), "yyyy-MM-dd");
			AdReportParam param = new AdReportParam();
			param.setToday(today);
			param.setOffset(offset);
			param.setPageSize(pageSize);
			Result<List<ReportAdEarningsDTO>> adPointResult = adSrvService.getReportEarnings(param);
			list = adPointResult.getModel();
		}
		return list;
	}

	@Override
	public boolean isStatusData() {
		return false;
	}

	
}
