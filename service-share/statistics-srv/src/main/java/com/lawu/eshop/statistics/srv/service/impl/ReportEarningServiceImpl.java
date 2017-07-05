package com.lawu.eshop.statistics.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.statistics.param.ReportEarningParam;
import com.lawu.eshop.statistics.srv.bo.ReportEarningsDailyBO;
import com.lawu.eshop.statistics.srv.domain.ReportEarningsDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportEarningsDailyDOExample;
import com.lawu.eshop.statistics.srv.domain.ReportEarningsMonthDO;
import com.lawu.eshop.statistics.srv.mapper.ReportEarningsDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportEarningsMonthDOMapper;
import com.lawu.eshop.statistics.srv.service.ReportEarningService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class ReportEarningServiceImpl implements ReportEarningService {
	
	@Autowired
	private ReportEarningsDailyDOMapper reportEarningsDailyDOMapper;
	
	@Autowired
	private ReportEarningsMonthDOMapper reportEarningsMonthDOMapper;
	

	@Override
	public void saveDaily(ReportEarningParam param) {
		
		ReportEarningsDailyDO reportEarningsDailyDO=new ReportEarningsDailyDO();
		reportEarningsDailyDO.setAdPoint(param.getAdPoint());
		reportEarningsDailyDO.setLovePoint(param.getLovePoint());
		reportEarningsDailyDO.setPlatformPoint(param.getPlatformPoint());
		reportEarningsDailyDO.setUserPoint(param.getUserPoint());
		reportEarningsDailyDO.setGmtReport(param.getGmtReport());
		reportEarningsDailyDO.setGmtCreate(new Date());
		reportEarningsDailyDOMapper.insert(reportEarningsDailyDO);
	}

	@Override
	public void saveMonth(ReportEarningParam param) {
		
		ReportEarningsMonthDO reportEarningsMonthDO=new ReportEarningsMonthDO();
		reportEarningsMonthDO.setAdPoint(param.getAdPoint());
		reportEarningsMonthDO.setLovePoint(param.getLovePoint());
		reportEarningsMonthDO.setPlatformPoint(param.getPlatformPoint());
		reportEarningsMonthDO.setUserPoint(param.getUserPoint());
		reportEarningsMonthDO.setGmtReport(param.getGmtReport());
		reportEarningsMonthDO.setGmtCreate(new Date());
		reportEarningsMonthDOMapper.insert(reportEarningsMonthDO);

	}
	
	
	@Override
	public List<ReportEarningsDailyBO> getDailyList(String reportDate) {
		ReportEarningsDailyDOExample example = new ReportEarningsDailyDOExample();
		Date begin = DateUtil.formatDate(reportDate+"-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date end = DateUtil.getLastDayOfMonth(begin);
		example.createCriteria().andGmtReportBetween(begin, end);
		List<ReportEarningsDailyDO> rntList = reportEarningsDailyDOMapper.selectByExample(example);
		List<ReportEarningsDailyBO> boList = new ArrayList<>();
		for(ReportEarningsDailyDO rdo : rntList){
			ReportEarningsDailyBO bo = new ReportEarningsDailyBO();
			bo.setGmtCreate(rdo.getGmtCreate());
			bo.setGmtReport(rdo.getGmtReport());
			bo.setId(rdo.getId());
			bo.setAdPoint(rdo.getAdPoint());
			bo.setLovePoint(rdo.getLovePoint());
			bo.setPlatformPoint(rdo.getPlatformPoint());
			bo.setUserPoint(rdo.getUserPoint());
			boList.add(bo);
		}
		return boList;
	}

}
