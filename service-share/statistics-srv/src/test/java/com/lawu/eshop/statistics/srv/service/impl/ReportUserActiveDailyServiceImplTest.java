package com.lawu.eshop.statistics.srv.service.impl;

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

import com.lawu.eshop.statistics.srv.bo.ReportUserActiveAreaDailyBO;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveBO;
import com.lawu.eshop.statistics.srv.mapper.ReportUserActiveDailyDOMapper;
import com.lawu.eshop.statistics.srv.service.ReportUserActiveDailyService;
import com.lawu.eshop.utils.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class ReportUserActiveDailyServiceImplTest {

	@Autowired
	private ReportUserActiveDailyService reportUserActiveDailyService;

	@Autowired
	private ReportUserActiveDailyDOMapper reportUserActiveDailyDOMapper;

	@Transactional
	@Rollback
	@Test
	public void saveUserActiveDaily() {
		Integer memberCount = 10;
		Integer merchantCount = 20;
		reportUserActiveDailyService.saveUserActiveDaily(memberCount, merchantCount);
		int i = reportUserActiveDailyDOMapper.countByExample(null);
		Assert.assertEquals(1, i);
	}

	@Transactional
	@Rollback
	@Test
	public void getUserActiveListDaily() {
		saveUserActiveDaily();
		String beginTime = "";
		String endTime = "";
		List<ReportUserActiveBO> list = reportUserActiveDailyService.getUserActiveListDaily(beginTime, endTime);
		Assert.assertEquals(1, list.size());
		beginTime = DateUtil.getDateFormat(DateUtil.getFirstDayOfMonth());
		endTime = DateUtil.getDateFormat(DateUtil.getLastDayOfMonth());
		list = reportUserActiveDailyService.getUserActiveListDaily(beginTime, endTime);
		Assert.assertEquals(1, list.size());
	}

	@Transactional
	@Rollback
	@Test
	public void getReportUserActiveAreaDailyList() {
		Date date = DateUtil.getDayBefore(DateUtil.getNowDate());
		String reportDate = DateUtil.getDateFormat(date);
		List<ReportUserActiveAreaDailyBO> list = reportUserActiveDailyService.getReportUserActiveAreaDailyList(reportDate);
		Assert.assertEquals(0, list.size());
	}

}
