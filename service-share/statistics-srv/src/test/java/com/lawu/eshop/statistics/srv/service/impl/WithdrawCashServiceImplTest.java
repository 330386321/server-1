package com.lawu.eshop.statistics.srv.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import com.lawu.eshop.statistics.param.ReportKCommonParam;
import com.lawu.eshop.statistics.srv.domain.ReportWithdrawDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportWithdrawMonthDO;
import com.lawu.eshop.statistics.srv.mapper.ReportWithdrawDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportWithdrawMonthDOMapper;
import com.lawu.eshop.statistics.srv.service.WithdrawCashService;
import com.lawu.eshop.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class WithdrawCashServiceImplTest {

	@Autowired
	private WithdrawCashService WithdrawCashService;
	
	@Autowired
	private ReportWithdrawDailyDOMapper reportWithdrawDailyDOMapper;
	
	@Autowired
	private ReportWithdrawMonthDOMapper reportWithdrawMonthDOMapper;
	
	@Transactional
	@Rollback
	@Test
	public void saveDaily() {
		ReportKCommonParam param = new ReportKCommonParam();
		param.setMemberMoney(new BigDecimal(10));
		param.setMerchantMoney(new BigDecimal(10));
		param.setTotalMoney(new BigDecimal(20));
		param.setGmtCreate(new Date());
		param.setGmtReport(new Date());
		WithdrawCashService.saveDaily(param);
		List<ReportWithdrawDailyDO> list = reportWithdrawDailyDOMapper.selectByExample(null);
		Assert.assertEquals(1, list.size());
	}
	
	@Transactional
	@Rollback
	@Test
	public void saveMonth() {
		ReportKCommonParam param = new ReportKCommonParam();
		param.setMemberMoney(new BigDecimal(10));
		param.setMerchantMoney(new BigDecimal(10));
		param.setTotalMoney(new BigDecimal(20));
		param.setGmtCreate(DateUtil.getFirstDayOfMonth(DateUtil.getMonthBefore(new Date())));
		param.setGmtReport(new Date());
		WithdrawCashService.saveMonth(param);
		List<ReportWithdrawMonthDO> list = reportWithdrawMonthDOMapper.selectByExample(null);
		Assert.assertEquals(1, list.size());
	}
	
	@Transactional
	@Rollback
	@Test
	public void getDailyList() {
		saveDaily();
		String reportDate = "2017-7";
		WithdrawCashService.getDailyList(reportDate);
	}
	
	
	@Transactional
	@Rollback
	@Test
	public void deleteDailyByReportDate() {
		saveDaily();
		WithdrawCashService.deleteDailyByReportDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		List<ReportWithdrawDailyDO> list = reportWithdrawDailyDOMapper.selectByExample(null);
		Assert.assertEquals(0, list.size());
	}
	
	@Transactional
	@Rollback
	@Test
	public void deleteMonthByReportDate() {
		saveMonth();
		WithdrawCashService.deleteMonthByReportDate(new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getMonthBefore(new Date())));
		List<ReportWithdrawDailyDO> list = reportWithdrawDailyDOMapper.selectByExample(null);
		Assert.assertEquals(0, list.size());
	}
	
	@Transactional
	@Rollback
	@Test
	public void selectReport() {
		saveDaily();
		saveMonth();
		String bdate = "";
		String edate = "";
		WithdrawCashService.selectReport(bdate, edate);
		bdate = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getFirstDayOfMonth());
		edate = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getFirstDayOfMonth());
		WithdrawCashService.selectReport(bdate, edate);
		bdate = new SimpleDateFormat("yyyy-MM").format(DateUtil.getMonthBefore(new Date()));
		edate = new SimpleDateFormat("yyyy-MM").format(new Date());
		WithdrawCashService.selectReport(bdate, edate);
	}
}
