package com.lawu.eshop.statistics.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.statistics.param.ReportKCommonParam;
import com.lawu.eshop.statistics.srv.bo.PointConsumeDailyBO;
import com.lawu.eshop.statistics.srv.domain.ReportPointConsumeDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportPointConsumeDailyDOExample;
import com.lawu.eshop.statistics.srv.domain.ReportPointConsumeMonthDO;
import com.lawu.eshop.statistics.srv.domain.ReportPointConsumeMonthDOExample;
import com.lawu.eshop.statistics.srv.mapper.ReportPointConsumeDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportPointConsumeMonthDOMapper;
import com.lawu.eshop.statistics.srv.service.PointConsumeService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class PointConsumeServiceImpl implements PointConsumeService {

	@Autowired
	private ReportPointConsumeDailyDOMapper reportPointConsumeDailyDOMapper;
	@Autowired
	private ReportPointConsumeMonthDOMapper reportPointConsumeMonthDOMapper;
	
	@Override
	public void saveDaily(ReportKCommonParam param) {
		ReportPointConsumeDailyDO record = new ReportPointConsumeDailyDO();
		record.setGmtCreate(param.getGmtCreate());
		record.setGmtReport(param.getGmtReport());
		record.setMemberPoint(param.getMemberMoney());
		record.setMerchantPoint(param.getMerchantMoney());
		record.setTotalPoint(param.getTotalMoney());
		reportPointConsumeDailyDOMapper.insertSelective(record);
	}

	@Override
	public void saveMonth(ReportKCommonParam param) {
		ReportPointConsumeMonthDO record = new ReportPointConsumeMonthDO();
		record.setGmtCreate(param.getGmtCreate());
		record.setGmtReport(param.getGmtReport());
		record.setMemberPoint(param.getMemberMoney());
		record.setMerchantPoint(param.getMerchantMoney());
		record.setTotalPoint(param.getTotalMoney());
		reportPointConsumeMonthDOMapper.insertSelective(record);
		
	}

	@Override
	public List<PointConsumeDailyBO> getDailyList(String reportDate) {
		ReportPointConsumeDailyDOExample example = new ReportPointConsumeDailyDOExample();
		Date begin = DateUtil.formatDate(reportDate+"-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date end = DateUtil.getLastDayOfMonth(begin);
		example.createCriteria().andGmtReportBetween(begin, end);
		List<ReportPointConsumeDailyDO> rntList = reportPointConsumeDailyDOMapper.selectByExample(example);
		List<PointConsumeDailyBO> boList = new ArrayList<>();
		for(ReportPointConsumeDailyDO rdo : rntList){
			PointConsumeDailyBO bo = new PointConsumeDailyBO();
			bo.setGmtCreate(rdo.getGmtCreate());
			bo.setGmtReport(rdo.getGmtReport());
			bo.setId(rdo.getId());
			bo.setMemberPoint(rdo.getMemberPoint());
			bo.setMerchantPoint(rdo.getMerchantPoint());
			bo.setTotalPoint(rdo.getTotalPoint());
			boList.add(bo);
		}
		return boList;
	}

	@Override
	public void deleteDailyByReportDate(String reportDate) {
		ReportPointConsumeDailyDOExample example = new ReportPointConsumeDailyDOExample();
		example.createCriteria().andGmtReportEqualTo(DateUtil.formatDate(reportDate, "yyyy-MM-dd"));
		reportPointConsumeDailyDOMapper.deleteByExample(example);
	}

	@Override
	public void deleteMonthByReportDate(String reportDate) {
		ReportPointConsumeMonthDOExample example = new ReportPointConsumeMonthDOExample();
		example.createCriteria().andGmtReportEqualTo(DateUtil.formatDate(reportDate, "yyyy-MM"));
		reportPointConsumeMonthDOMapper.deleteByExample(example);
	}
	
}
