package com.lawu.eshop.statistics.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.statistics.param.ReportWithdrawParam;
import com.lawu.eshop.statistics.srv.bo.ReportWithdrawDailyBO;
import com.lawu.eshop.statistics.srv.domain.ReportWithdrawDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportWithdrawDailyDOExample;
import com.lawu.eshop.statistics.srv.domain.ReportWithdrawMonthDO;
import com.lawu.eshop.statistics.srv.domain.ReportWithdrawMonthDOExample;
import com.lawu.eshop.statistics.srv.mapper.ReportWithdrawDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportWithdrawMonthDOMapper;
import com.lawu.eshop.statistics.srv.service.WithdrawCashService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class WithdrawCashServiceImpl implements WithdrawCashService {

	@Autowired
	private ReportWithdrawDailyDOMapper reportWithdrawDailyDOMapper;
	@Autowired
	private ReportWithdrawMonthDOMapper reportWithdrawMonthDOMapper;
	
	@Override
	public void saveDaily(ReportWithdrawParam param) {
		ReportWithdrawDailyDO record = new ReportWithdrawDailyDO();
		record.setGmtCreate(param.getGmtCreate());
		record.setGmtReport(param.getGmtReport());
		record.setMoney(param.getMoney());
		record.setUserType(param.getUserType());
		reportWithdrawDailyDOMapper.insertSelective(record);
	}

	@Override
	public void saveMonth(ReportWithdrawParam param) {
		ReportWithdrawMonthDO record = new ReportWithdrawMonthDO();
		record.setGmtCreate(param.getGmtCreate());
		record.setGmtReport(param.getGmtReport());
		record.setMoney(param.getMoney());
		record.setUserType(param.getUserType());
		reportWithdrawMonthDOMapper.insertSelective(record);
		
	}

	@Override
	public List<ReportWithdrawDailyBO> getDailyList(String reportDate) {
		ReportWithdrawDailyDOExample example = new ReportWithdrawDailyDOExample();
		Date begin = DateUtil.formatDate(reportDate+"-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date end = DateUtil.getLastDayOfMonth(begin);
		example.createCriteria().andGmtReportBetween(begin, end);
		List<ReportWithdrawDailyDO> rntList = reportWithdrawDailyDOMapper.selectByExample(example);
		List<ReportWithdrawDailyBO> boList = new ArrayList<>();
		for(ReportWithdrawDailyDO rdo : rntList){
			ReportWithdrawDailyBO bo = new ReportWithdrawDailyBO();
			bo.setGmtCreate(rdo.getGmtCreate());
			bo.setGmtReport(rdo.getGmtReport());
			bo.setId(rdo.getId());
			bo.setMoney(rdo.getMoney());
			bo.setUserType(rdo.getUserType());
			boList.add(bo);
		}
		return boList;
	}

	@Override
	public void deleteDailyByReportDate(String reportDate) {
		ReportWithdrawDailyDOExample example = new ReportWithdrawDailyDOExample();
		example.createCriteria().andGmtReportEqualTo(DateUtil.formatDate(reportDate, "yyyy-MM-dd"));
		reportWithdrawDailyDOMapper.deleteByExample(example);
	}

	@Override
	public void deleteMonthByReportDate(String reportDate) {
		ReportWithdrawMonthDOExample example = new ReportWithdrawMonthDOExample();
		example.createCriteria().andGmtReportEqualTo(DateUtil.formatDate(reportDate, "yyyy-MM"));
		reportWithdrawMonthDOMapper.deleteByExample(example);
	}
	
}
