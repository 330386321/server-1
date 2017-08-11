package com.lawu.eshop.statistics.srv.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.statistics.param.ReportAreaAdPointMonthParams;
import com.lawu.eshop.statistics.srv.domain.ReportAreaAdPointMonthDO;
import com.lawu.eshop.statistics.srv.mapper.ReportAreaAdPointMonthDOMapper;
import com.lawu.eshop.statistics.srv.service.ReportAreaAdPointMonthService;
import com.lawu.eshop.utils.DateUtil;
@Service
public class ReportAreaAdPointMonthServiceImpl implements ReportAreaAdPointMonthService{

	@Autowired
	private ReportAreaAdPointMonthDOMapper reportAreaAdPointMonthDOMapper;
	
	@Override
	public int insertReportAreaAdPointMonth(ReportAreaAdPointMonthParams param) {
		ReportAreaAdPointMonthDO reportAreaAdPointMonthDO = new ReportAreaAdPointMonthDO();
		reportAreaAdPointMonthDO.setAreaId(param.getAreaId());
		reportAreaAdPointMonthDO.setCityId(param.getCityId());
		reportAreaAdPointMonthDO.setGmtCreate(new Date());
		reportAreaAdPointMonthDO.setGmtReport(DateUtil.getFirstDayOfMonth(DateUtil.getMonthBefore(new Date())));
		reportAreaAdPointMonthDO.setProvinceId(param.getProvinceId());
		reportAreaAdPointMonthDO.setReportTotalPoint(param.getReportTotalPoint());
		return reportAreaAdPointMonthDOMapper.insertSelective(reportAreaAdPointMonthDO);
	}

}
