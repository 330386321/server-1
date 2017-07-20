package com.lawu.eshop.statistics.srv.converter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lawu.eshop.statistics.param.PlatformTotalSalesSaveParam;
import com.lawu.eshop.statistics.srv.domain.ReportSalesDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportSalesMonthDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class ReportSalesMonthConverterTest {
	
	@Test
	public void convert() {
		try {
			PlatformTotalSalesSaveParam param = new PlatformTotalSalesSaveParam();
			param.setGmtReport(new Date());
			ReportSalesDailyDO reportSalesDailyDO = new ReportSalesDailyDO();
			reportSalesDailyDO.setPayOrderAmount(new BigDecimal(10));
			reportSalesDailyDO.setShoppingOrderAmount(new BigDecimal(20));
			List<ReportSalesDailyDO> reportSalesDailyDOList = new ArrayList<ReportSalesDailyDO>();
			reportSalesDailyDOList.add(reportSalesDailyDO);
			ReportSalesMonthConverter.convert(param, reportSalesDailyDOList);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void convertDOtoBo() {
		try {
			ReportSalesMonthDO reportSalesMonthDO = new ReportSalesMonthDO();
			reportSalesMonthDO.setGmtCreate(new Date());
			reportSalesMonthDO.setGmtReport(new Date());
			reportSalesMonthDO.setPayOrderAmount(new BigDecimal(10));
			reportSalesMonthDO.setShoppingOrderAmount(new BigDecimal(20));
			reportSalesMonthDO.setTotalAmount(new BigDecimal(30));
			ReportSalesMonthConverter.convert(reportSalesMonthDO);
			reportSalesMonthDO = null;
			ReportSalesMonthConverter.convert(reportSalesMonthDO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void convertReportSalesBOList() {
		try {
			List<ReportSalesMonthDO> reportSalesMonthDOList = new ArrayList<ReportSalesMonthDO>();
			ReportSalesMonthConverter.convertReportSalesBOList(reportSalesMonthDOList);
			ReportSalesMonthDO reportSalesDailyDO = new ReportSalesMonthDO();
			reportSalesMonthDOList.add(reportSalesDailyDO);
			ReportSalesMonthConverter.convertReportSalesBOList(reportSalesMonthDOList);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
}
