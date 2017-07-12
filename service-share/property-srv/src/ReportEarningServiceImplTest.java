package com.lawu.eshop.statistics.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.statistics.param.ReportEarningParam;
import com.lawu.eshop.statistics.srv.bo.ReportCommonEarningsBO;
import com.lawu.eshop.statistics.srv.bo.ReportEarningsDailyBO;
import com.lawu.eshop.statistics.srv.domain.ReportEarningsDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportEarningsDailyDOExample;
import com.lawu.eshop.statistics.srv.domain.ReportEarningsMonthDO;
import com.lawu.eshop.statistics.srv.domain.ReportEarningsMonthDOExample;
import com.lawu.eshop.statistics.srv.mapper.ReportEarningsDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportEarningsMonthDOMapper;
import com.lawu.eshop.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class ReportEarningServiceImplTest {
	
	@Autowired
	private ReportEarningServiceImpl reportEarningServiceImpl;
	
	@Autowired
	private ReportEarningsDailyDOMapper reportEarningsDailyDOMapper;
	
	@Autowired
	private ReportEarningsMonthDOMapper reportEarningsMonthDOMapper;

	@Transactional
	@Rollback
	@Test
	public void saveDaily(ReportEarningParam param) {
		ReportEarningParam reportEarningParam = new ReportEarningParam();
		reportEarningParam.setAdPoint(new BigDecimal(10));
		reportEarningParam.setLovePoint(new BigDecimal(10));
		reportEarningParam.setPlatformPoint(new BigDecimal(10));
		reportEarningParam.setUserPoint(new BigDecimal(10));
		reportEarningParam.setGmtReport(new Date());
		reportEarningServiceImpl.saveDaily(param);
		long i = reportEarningsDailyDOMapper.countByExample(null);
		Assert.assertEquals(1L, i);
	}

	@Transactional
	@Rollback
	@Test
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
	
	
	@Transactional
	@Rollback
	@Test
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
	
	
	@Transactional
	@Rollback
	@Test
	public ReportCommonEarningsBO selectReport(String bdate, String edate) {
		if("".equals(bdate) || "".equals(edate)){
			bdate = DateUtil.getDateFormat(new Date(), "yyyy-MM")+"-01";
			edate = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
		}
		List<String> xAxisData = new ArrayList<>();
		List<BigDecimal> yAxisAdPointData = new ArrayList<>();
		List<BigDecimal> yAxisUserPointData = new ArrayList<>();
		List<BigDecimal> yAxisLovePointData = new ArrayList<>();
		List<BigDecimal> yAxisPlateformPointData = new ArrayList<>();
		if(bdate.length() > 7){
			ReportEarningsDailyDOExample example = new ReportEarningsDailyDOExample();
			Date begin = DateUtil.formatDate(bdate, "yyyy-MM-dd");
			Date end = DateUtil.formatDate(edate, "yyyy-MM-dd");
			example.createCriteria().andGmtReportBetween(begin, end);
			example.setOrderByClause(" gmt_report asc ");
			List<ReportEarningsDailyDO> rntList = reportEarningsDailyDOMapper.selectByExample(example);
			for(ReportEarningsDailyDO rdo : rntList){
				String day = DateUtil.getDateFormat(rdo.getGmtReport(), "MM-dd");
				xAxisData.add(day);
				yAxisAdPointData.add(rdo.getAdPoint().setScale(2));
				yAxisUserPointData.add(rdo.getUserPoint().setScale(2));
				yAxisLovePointData.add(rdo.getLovePoint().setScale(2));
				yAxisPlateformPointData.add(rdo.getPlatformPoint().setScale(2));
			}
		}else {
			ReportEarningsMonthDOExample example = new ReportEarningsMonthDOExample();
			Date begin = DateUtil.formatDate(bdate+"-01", "yyyy-MM-dd");
			Date endFirst = DateUtil.formatDate(edate+"-01", "yyyy-MM-dd");
			Date end = DateUtil.getLastDayOfMonth(endFirst);
			example.createCriteria().andGmtReportBetween(begin, end);
			example.setOrderByClause(" gmt_report asc ");
			List<ReportEarningsMonthDO> rntList = reportEarningsMonthDOMapper.selectByExample(example);
			for(ReportEarningsMonthDO rdo : rntList){
				String day = DateUtil.getDateFormat(rdo.getGmtReport(), "yyyy-MM");
				xAxisData.add(day);
				yAxisAdPointData.add(rdo.getAdPoint().setScale(2));
				yAxisUserPointData.add(rdo.getUserPoint().setScale(2));
				yAxisLovePointData.add(rdo.getLovePoint().setScale(2));
				yAxisPlateformPointData.add(rdo.getPlatformPoint().setScale(2));
			}
		}
		ReportCommonEarningsBO bo=new ReportCommonEarningsBO();
		bo.setxAxisData(xAxisData);
		bo.setyAxisAdPointData(yAxisAdPointData);
		bo.setyAxisUserPointData(yAxisUserPointData);
		bo.setyAxisPlateformPointData(yAxisPlateformPointData);
		bo.setyAxisLovePointData(yAxisLovePointData);
		bo.setBdate(bdate);
		bo.setEdate(edate);
		return bo;
	}
}
