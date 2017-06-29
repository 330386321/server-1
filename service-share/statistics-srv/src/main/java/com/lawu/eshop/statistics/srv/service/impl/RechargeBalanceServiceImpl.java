package com.lawu.eshop.statistics.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.statistics.param.ReportKCommonParam;
import com.lawu.eshop.statistics.srv.bo.RechargeBalanceDailyBO;
import com.lawu.eshop.statistics.srv.domain.ReportRechargeBalanceDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportRechargeBalanceDailyDOExample;
import com.lawu.eshop.statistics.srv.domain.ReportRechargeBalanceMonthDO;
import com.lawu.eshop.statistics.srv.domain.ReportRechargeBalanceMonthDOExample;
import com.lawu.eshop.statistics.srv.mapper.ReportRechargeBalanceDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportRechargeBalanceMonthDOMapper;
import com.lawu.eshop.statistics.srv.service.RechargeBalanceService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class RechargeBalanceServiceImpl implements RechargeBalanceService {

	@Autowired
	private ReportRechargeBalanceDailyDOMapper reportRechargeBalanceDailyDOMapper;
	@Autowired
	private ReportRechargeBalanceMonthDOMapper reportRechargeBalanceMonthDOMapper;
	
	@Override
	public void saveDaily(ReportKCommonParam param) {
		ReportRechargeBalanceDailyDO record = new ReportRechargeBalanceDailyDO();
		record.setGmtCreate(param.getGmtCreate());
		record.setGmtReport(param.getGmtReport());
		record.setMemberMoney(param.getMemberMoney());
		record.setMerchantMoney(param.getMerchantMoney());
		record.setTotalMoney(param.getTotalMoney());
		reportRechargeBalanceDailyDOMapper.insertSelective(record);
	}

	@Override
	public void saveMonth(ReportKCommonParam param) {
		ReportRechargeBalanceMonthDO record = new ReportRechargeBalanceMonthDO();
		record.setGmtCreate(param.getGmtCreate());
		record.setGmtReport(param.getGmtReport());
		record.setMemberMoney(param.getMemberMoney());
		record.setMerchantMoney(param.getMerchantMoney());
		record.setTotalMoney(param.getTotalMoney());
		reportRechargeBalanceMonthDOMapper.insertSelective(record);
		
	}

	@Override
	public List<RechargeBalanceDailyBO> getDailyList(String reportDate) {
		ReportRechargeBalanceDailyDOExample example = new ReportRechargeBalanceDailyDOExample();
		Date begin = DateUtil.formatDate(reportDate+"-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date end = DateUtil.getLastDayOfMonth(begin);
		example.createCriteria().andGmtReportBetween(begin, end);
		List<ReportRechargeBalanceDailyDO> rntList = reportRechargeBalanceDailyDOMapper.selectByExample(example);
		List<RechargeBalanceDailyBO> boList = new ArrayList<>();
		for(ReportRechargeBalanceDailyDO rdo : rntList){
			RechargeBalanceDailyBO bo = new RechargeBalanceDailyBO();
			bo.setGmtCreate(rdo.getGmtCreate());
			bo.setGmtReport(rdo.getGmtReport());
			bo.setId(rdo.getId());
			bo.setMemberMoney(rdo.getMemberMoney());
			bo.setMerchantMoney(rdo.getMerchantMoney());
			bo.setTotalMoney(rdo.getTotalMoney());
			boList.add(bo);
		}
		return boList;
	}

	@Override
	public void deleteDailyByReportDate(String reportDate) {
		ReportRechargeBalanceDailyDOExample example = new ReportRechargeBalanceDailyDOExample();
		example.createCriteria().andGmtReportEqualTo(DateUtil.formatDate(reportDate, "yyyy-MM-dd"));
		reportRechargeBalanceDailyDOMapper.deleteByExample(example);
	}

	@Override
	public void deleteMonthByReportDate(String reportDate) {
		ReportRechargeBalanceMonthDOExample example = new ReportRechargeBalanceMonthDOExample();
		example.createCriteria().andGmtReportEqualTo(DateUtil.formatDate(reportDate, "yyyy-MM"));
		reportRechargeBalanceMonthDOMapper.deleteByExample(example);
	}
	
}
