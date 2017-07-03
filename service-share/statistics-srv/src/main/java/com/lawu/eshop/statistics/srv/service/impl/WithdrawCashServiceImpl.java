package com.lawu.eshop.statistics.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.statistics.dto.ReportCommonBackDTO;
import com.lawu.eshop.statistics.param.ReportKCommonParam;
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
	public void saveDaily(ReportKCommonParam param) {
		ReportWithdrawDailyDO record = new ReportWithdrawDailyDO();
		record.setGmtCreate(param.getGmtCreate());
		record.setGmtReport(param.getGmtReport());
		record.setMemberMoney(param.getMemberMoney());
		record.setMerchantMoney(param.getMerchantMoney());
		record.setTotalMoney(param.getTotalMoney());
		reportWithdrawDailyDOMapper.insertSelective(record);
	}

	@Override
	public void saveMonth(ReportKCommonParam param) {
		ReportWithdrawMonthDO record = new ReportWithdrawMonthDO();
		record.setGmtCreate(param.getGmtCreate());
		record.setGmtReport(param.getGmtReport());
		record.setMemberMoney(param.getMemberMoney());
		record.setMerchantMoney(param.getMerchantMoney());
		record.setTotalMoney(param.getTotalMoney());
		reportWithdrawMonthDOMapper.insertSelective(record);
		
	}

	@Override
	public List<ReportWithdrawDailyBO> getDailyList(String reportDate) {
		ReportWithdrawDailyDOExample example = new ReportWithdrawDailyDOExample();
		Date begin = DateUtil.formatDate(reportDate+"-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date end = DateUtil.getDayAfter(DateUtil.getLastDayOfMonth(begin));
		example.createCriteria().andGmtReportBetween(begin, end);
		List<ReportWithdrawDailyDO> rntList = reportWithdrawDailyDOMapper.selectByExample(example);
		List<ReportWithdrawDailyBO> boList = new ArrayList<>();
		for(ReportWithdrawDailyDO rdo : rntList){
			ReportWithdrawDailyBO bo = new ReportWithdrawDailyBO();
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

	@Override
	public ReportCommonBackDTO selectReport(String flag,String date,String isTotal) {
		if("".equals(date)){
			date = DateUtil.getDateFormat(new Date(), "yyyy-MM");
		}
		ReportCommonBackDTO dto = new ReportCommonBackDTO();
		List<String> xAxisData = new ArrayList<>();
		List<BigDecimal> yAxisMemberData = new ArrayList<>();
		List<BigDecimal> yAxisMerchantData = new ArrayList<>();
		List<BigDecimal> yAxisTotalData = new ArrayList<>();
		if("1".equals(flag)){
			ReportWithdrawDailyDOExample example = new ReportWithdrawDailyDOExample();
			Date begin = DateUtil.formatDate(date+"-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Date end = DateUtil.getDayAfter(DateUtil.getLastDayOfMonth(begin));
			example.createCriteria().andGmtReportBetween(begin, end);
			example.setOrderByClause(" gmt_report asc ");
			List<ReportWithdrawDailyDO> rntList = reportWithdrawDailyDOMapper.selectByExample(example);
			for(ReportWithdrawDailyDO rdo : rntList){
				String day = DateUtil.getDateFormat(rdo.getGmtReport(), "dd");
				xAxisData.add(day);
				if("0".equals(isTotal)){
					yAxisMemberData.add(rdo.getMemberMoney().setScale(2));
					yAxisMerchantData.add(rdo.getMerchantMoney().setScale(2));
				}else{
					yAxisTotalData.add(rdo.getTotalMoney().setScale(2));
				}
			}
		}else if("2".equals(flag)){
			ReportWithdrawMonthDOExample example = new ReportWithdrawMonthDOExample();
			Date begin = DateUtil.formatDate(date+"-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
			Date end = DateUtil.getDayAfter(DateUtil.getLastDayOfMonth(begin));
			example.createCriteria().andGmtReportBetween(begin, end);
			example.setOrderByClause(" gmt_report asc ");
			List<ReportWithdrawMonthDO> rntList = reportWithdrawMonthDOMapper.selectByExample(example);
			for(ReportWithdrawMonthDO rdo : rntList){
				String day = DateUtil.getDateFormat(rdo.getGmtReport(), "MM");
				xAxisData.add(day);
				if("0".equals(isTotal)){
					yAxisMemberData.add(rdo.getMemberMoney().setScale(2));
					yAxisMerchantData.add(rdo.getMerchantMoney().setScale(2));
				}else{
					yAxisTotalData.add(rdo.getTotalMoney().setScale(2));
				}
			}
		}
		dto.setxAxisData(xAxisData);
		dto.setyAxisMemberData(yAxisMemberData);
		dto.setyAxisMerchantData(yAxisMerchantData);
		dto.setyAxisTotalData(yAxisTotalData);
		return dto;
	}
	
}
