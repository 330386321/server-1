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

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.statistics.constants.UserTypeEnum;
import com.lawu.eshop.statistics.param.ReportUserIncomeExpenditureSaveParam;
import com.lawu.eshop.statistics.srv.bo.ReportUserIncomeExpenditureBO;
import com.lawu.eshop.statistics.srv.domain.ReportUserIncomeExpenditureDO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class ReportUserIncomeExpenditureConverterTest {

	
	@Test
	public void convert() {
		try {
			ReportUserIncomeExpenditureSaveParam param = new ReportUserIncomeExpenditureSaveParam();
			param.setAccount("13025458808");
			param.setUserNum("M13025458808");
			param.setIncome(new BigDecimal(20));
			param.setExpenditure(new BigDecimal(10));
			ReportUserIncomeExpenditureConverter.convert(param);
			param = null;
			ReportUserIncomeExpenditureConverter.convert(param);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void convertDOtoBO() {
		try {
			ReportUserIncomeExpenditureDO reportUserIncomeExpenditureDO = new ReportUserIncomeExpenditureDO();
			reportUserIncomeExpenditureDO.setAccount("13025458808");
			reportUserIncomeExpenditureDO.setId(1302545L);
			reportUserIncomeExpenditureDO.setIncome(new BigDecimal(10));
			reportUserIncomeExpenditureDO.setUserNum("M13025458808");
			reportUserIncomeExpenditureDO.setUserType((byte)1);
			reportUserIncomeExpenditureDO.setGmtReport(new Date());
			reportUserIncomeExpenditureDO.setGmtCreate(new Date());
			reportUserIncomeExpenditureDO.setDifference(new BigDecimal(10));
			reportUserIncomeExpenditureDO.setExpenditure(new BigDecimal(1));
			ReportUserIncomeExpenditureConverter.convert(reportUserIncomeExpenditureDO);
			reportUserIncomeExpenditureDO = null;
			ReportUserIncomeExpenditureConverter.convert(reportUserIncomeExpenditureDO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void convertReportUserIncomeExpenditureBOList() {
		try {
			List<ReportUserIncomeExpenditureDO> reportUserIncomeExpenditureDOList = new ArrayList<ReportUserIncomeExpenditureDO>();
			ReportUserIncomeExpenditureConverter.convertReportUserIncomeExpenditureBOList(reportUserIncomeExpenditureDOList);
			ReportUserIncomeExpenditureDO reportUserIncomeExpenditureDO = new ReportUserIncomeExpenditureDO();
			reportUserIncomeExpenditureDO.setAccount("13025458808");
			reportUserIncomeExpenditureDO.setId(1302545L);
			reportUserIncomeExpenditureDO.setIncome(new BigDecimal(10));
			reportUserIncomeExpenditureDO.setUserNum("M13025458808");
			reportUserIncomeExpenditureDO.setUserType((byte)1);
			reportUserIncomeExpenditureDO.setGmtReport(new Date());
			reportUserIncomeExpenditureDO.setGmtCreate(new Date());
			reportUserIncomeExpenditureDO.setDifference(new BigDecimal(10));
			reportUserIncomeExpenditureDO.setExpenditure(new BigDecimal(1));
			reportUserIncomeExpenditureDOList.add(reportUserIncomeExpenditureDO);
			ReportUserIncomeExpenditureConverter.convertReportUserIncomeExpenditureBOList(reportUserIncomeExpenditureDOList);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void convertBOtoDTO() {
		try {
			ReportUserIncomeExpenditureBO reportUserIncomeExpenditureBO = new ReportUserIncomeExpenditureBO();
			reportUserIncomeExpenditureBO.setAccount("13025458808");
			reportUserIncomeExpenditureBO.setUserNum("M13025458808");
			reportUserIncomeExpenditureBO.setDifference(new BigDecimal(10));
			reportUserIncomeExpenditureBO.setExpenditure(new BigDecimal(10));
			reportUserIncomeExpenditureBO.setGmtCreate(new Date());
			reportUserIncomeExpenditureBO.setGmtReport(new Date());
			reportUserIncomeExpenditureBO.setIncome(new BigDecimal(20));
			reportUserIncomeExpenditureBO.setUserType(UserTypeEnum.MEMBER);
			ReportUserIncomeExpenditureConverter.convert(reportUserIncomeExpenditureBO);
			reportUserIncomeExpenditureBO = null;
			ReportUserIncomeExpenditureConverter.convert(reportUserIncomeExpenditureBO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void convertReportUserIncomeExpenditureDTOList() {
		try {
			List<ReportUserIncomeExpenditureBO> reportUserIncomeExpenditureBOList = new ArrayList<ReportUserIncomeExpenditureBO>();
			ReportUserIncomeExpenditureConverter.convertReportUserIncomeExpenditureDTOList(reportUserIncomeExpenditureBOList);
			ReportUserIncomeExpenditureBO reportUserIncomeExpenditureBO = new ReportUserIncomeExpenditureBO();
			reportUserIncomeExpenditureBO.setAccount("13025458808");
			reportUserIncomeExpenditureBO.setUserNum("M13025458808");
			reportUserIncomeExpenditureBO.setDifference(new BigDecimal(10));
			reportUserIncomeExpenditureBO.setExpenditure(new BigDecimal(10));
			reportUserIncomeExpenditureBO.setGmtCreate(new Date());
			reportUserIncomeExpenditureBO.setGmtReport(new Date());
			reportUserIncomeExpenditureBO.setIncome(new BigDecimal(20));
			reportUserIncomeExpenditureBO.setUserType(UserTypeEnum.MEMBER);
			reportUserIncomeExpenditureBOList.add(reportUserIncomeExpenditureBO);
			ReportUserIncomeExpenditureConverter.convertReportUserIncomeExpenditureDTOList(reportUserIncomeExpenditureBOList);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	
	@Test
	public void convertDOPpageToBOPpage() {
		try {
			Page<ReportUserIncomeExpenditureBO> page = new Page<ReportUserIncomeExpenditureBO>();
			List<ReportUserIncomeExpenditureBO> list = new ArrayList<ReportUserIncomeExpenditureBO>();
			page.setRecords(list);
			page.setCurrentPage(10);
			page.setTotalCount(100);
			ReportUserIncomeExpenditureConverter.convert(page);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
