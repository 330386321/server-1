package com.lawu.eshop.statistics.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.statistics.param.PlatformTotalSalesQueryParam;
import com.lawu.eshop.statistics.param.PlatformTotalSalesSaveParam;
import com.lawu.eshop.statistics.srv.bo.ReportSalesBO;
import com.lawu.eshop.statistics.srv.converter.ReportSalesDailyConverter;
import com.lawu.eshop.statistics.srv.converter.ReportSalesMonthConverter;
import com.lawu.eshop.statistics.srv.domain.ReportSalesDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportSalesDailyDOExample;
import com.lawu.eshop.statistics.srv.domain.ReportSalesMonthDO;
import com.lawu.eshop.statistics.srv.domain.ReportSalesMonthDOExample;
import com.lawu.eshop.statistics.srv.mapper.ReportSalesDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportSalesMonthDOMapper;
import com.lawu.eshop.statistics.srv.service.ReportSalesService;
import com.lawu.eshop.utils.DateUtil;

/**
 * 平台总销量服务接口实现类
 * 
 * @author Sunny
 * @date 2017年6月30日
 */
@Service
public class ReportSalesServiceImpl implements ReportSalesService {

	@Autowired
	private ReportSalesDailyDOMapper reportSalesDailyDOMapper;

	@Autowired
	private ReportSalesMonthDOMapper reportSalesMonthDOMapper;

	/**
	 * 保存平台总销量记录
	 * 
	 * @param param
	 * @author Sunny
	 * @date 2017年7月3日
	 */
	@Override
	@Transactional
	public void save(PlatformTotalSalesSaveParam param) {
		switch (param.getType()) {
			case DAILY:
				ReportSalesDailyDO reportSalesDailyDO = ReportSalesDailyConverter.convert(param);
				reportSalesDailyDOMapper.insertSelective(reportSalesDailyDO);
				break;
			case MONTH:
				Date start = DateUtil.getFirstDayOfMonth(param.getGmtReport());
				Date end = DateUtil.getLastDayOfMonth(param.getGmtReport());
				ReportSalesDailyDOExample reportSalesDailyDOExample = new ReportSalesDailyDOExample();
				reportSalesDailyDOExample.createCriteria().andGmtReportBetween(start, end);
				List<ReportSalesDailyDO> reportSalesDailyDOList = reportSalesDailyDOMapper.selectByExample(reportSalesDailyDOExample);
				ReportSalesMonthDO reportSalesMonthDO = ReportSalesMonthConverter.convert(param, reportSalesDailyDOList);
				reportSalesMonthDOMapper.insertSelective(reportSalesMonthDO);
				break;
			default:
				break;
		}

	}

    /**
     * 查询平台总销量记录
     * 
     * @param param
     * @return
     * @author Sunny
     * @date 2017年7月3日
     */
	@Override
	public List<ReportSalesBO> list(PlatformTotalSalesQueryParam param) {
		List<ReportSalesBO> rtn = null;
		Date start = null;
		Date end = null;
		switch (param.getType()) {
			case DAILY:
				ReportSalesDailyDOExample reportSalesDailyDOExample = new ReportSalesDailyDOExample();
				start = DateUtil.getFirstSecondOfDay(DateUtil.getFirstDayOfMonth(param.getDate()));
				end = DateUtil.getLastSecondOfDay(DateUtil.getLastDayOfMonth(param.getDate()));
				reportSalesDailyDOExample.createCriteria().andGmtReportBetween(start, end);
				List<ReportSalesDailyDO> reportSalesDailyDOList = reportSalesDailyDOMapper.selectByExample(reportSalesDailyDOExample);
				rtn = ReportSalesDailyConverter.convertReportSalesBOList(reportSalesDailyDOList);
				break;
			case MONTH:
				start = DateUtil.getFirstDayOfMonth(DateUtil.getFirstMonthOfYear(param.getDate()));
				end = DateUtil.getLastDayOfMonth(DateUtil.getLastMonthOfYear(param.getDate()));
				ReportSalesMonthDOExample reportSalesMonthDOExample = new ReportSalesMonthDOExample();
				reportSalesMonthDOExample.createCriteria().andGmtReportBetween(start, end);
				List<ReportSalesMonthDO> reportSalesMonthDOList = reportSalesMonthDOMapper.selectByExample(reportSalesMonthDOExample);
				rtn = ReportSalesMonthConverter.convertReportSalesBOList(reportSalesMonthDOList);
				break;
			default:
				break;
		}
		return rtn;
	}
}
