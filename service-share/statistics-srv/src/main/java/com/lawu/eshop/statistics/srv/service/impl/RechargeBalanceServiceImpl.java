package com.lawu.eshop.statistics.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.statistics.dto.ReportCommonBackDTO;
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

	@Override
	public ReportCommonBackDTO selectReport(String date, String isTotal) {
		if("".equals(date)){
			date = DateUtil.getDateFormat(new Date(), "yyyy-MM");
		}
		ReportCommonBackDTO dto = new ReportCommonBackDTO();
		List<String> xAxisData = new ArrayList<>();
		List<BigDecimal> yAxisMemberData = new ArrayList<>();
		List<BigDecimal> yAxisMerchantData = new ArrayList<>();
		List<BigDecimal> yAxisTotalData = new ArrayList<>();
		if(date.length() > 4){
			ReportRechargeBalanceDailyDOExample example = new ReportRechargeBalanceDailyDOExample();
			Date begin = DateUtil.formatDate(date+"-01", "yyyy-MM-dd");
			Date end = DateUtil.getLastDayOfMonth(begin);
			example.createCriteria().andGmtReportBetween(begin, end);
			example.setOrderByClause(" gmt_report asc ");
			List<ReportRechargeBalanceDailyDO> rntList = reportRechargeBalanceDailyDOMapper.selectByExample(example);
			for(ReportRechargeBalanceDailyDO rdo : rntList){
				String day = DateUtil.getDateFormat(rdo.getGmtReport(), "dd");
				xAxisData.add(day);
				if("0".equals(isTotal)){
					yAxisMemberData.add(rdo.getMemberMoney().setScale(2));
					yAxisMerchantData.add(rdo.getMerchantMoney().setScale(2));
				}else{
					yAxisTotalData.add(rdo.getTotalMoney().setScale(2));
				}
			}
		}else {
			ReportRechargeBalanceMonthDOExample example = new ReportRechargeBalanceMonthDOExample();
			Date begin = DateUtil.formatDate(date+"-01-01", "yyyy-MM-dd");
			Date end = DateUtil.formatDate(date+"-12-01", "yyyy-MM-dd");
			example.createCriteria().andGmtReportBetween(begin, end);
			example.setOrderByClause(" gmt_report asc ");
			List<ReportRechargeBalanceMonthDO> rntList = reportRechargeBalanceMonthDOMapper.selectByExample(example);
			for(ReportRechargeBalanceMonthDO rdo : rntList){
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
		dto.setDate(date);
		return dto;
	}
	
}
