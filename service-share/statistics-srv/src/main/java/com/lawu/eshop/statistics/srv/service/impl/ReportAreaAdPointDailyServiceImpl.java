package com.lawu.eshop.statistics.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.statistics.param.ReportAreaAdPointDailyParams;
import com.lawu.eshop.statistics.srv.bo.ReportAreaAdPointDailyBO;
import com.lawu.eshop.statistics.srv.bo.ReportAreaAdPointMonthBO;
import com.lawu.eshop.statistics.srv.domain.ReportAreaAdPointDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportAreaAdPointDailyDOExample;
import com.lawu.eshop.statistics.srv.domain.extend.ReportAreaAdPointDailyInMonthDOView;
import com.lawu.eshop.statistics.srv.mapper.ReportAreaAdPointDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.extend.ReportAreaAdPointDailyDOMapperExtend;
import com.lawu.eshop.statistics.srv.service.ReportAreaAdPointDailyService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class ReportAreaAdPointDailyServiceImpl implements ReportAreaAdPointDailyService{

	@Autowired
	private ReportAreaAdPointDailyDOMapper reportAreaAdPointDailyDOMapper;
	
	@Autowired
	private ReportAreaAdPointDailyDOMapperExtend reportAreaAdPointDailyDOMapperExtend;
	
	@Override
	public List<ReportAreaAdPointDailyBO> selectReportAreaAdPointDaily(Integer areaId, String date) {
		ReportAreaAdPointDailyDOExample reportAreaAdPointDailyDOExample = new ReportAreaAdPointDailyDOExample();
		reportAreaAdPointDailyDOExample.createCriteria().andAreaIdEqualTo(areaId).andGmtReportEqualTo(DateUtil.formatDate(date, "yyyy-MM-dd"));
		List<ReportAreaAdPointDailyDO> list = reportAreaAdPointDailyDOMapper.selectByExample(reportAreaAdPointDailyDOExample);
		List<ReportAreaAdPointDailyBO> rtnList = new ArrayList<ReportAreaAdPointDailyBO>();
		for(ReportAreaAdPointDailyDO DO : list) {
			ReportAreaAdPointDailyBO reportAreaAdPointDailyBO = new ReportAreaAdPointDailyBO();
			reportAreaAdPointDailyBO.setAreaId(DO.getAreaId());
			reportAreaAdPointDailyBO.setCityId(DO.getCityId());
			reportAreaAdPointDailyBO.setGmtCreate(DO.getGmtCreate());
			reportAreaAdPointDailyBO.setGmtReport(DO.getGmtReport());
			reportAreaAdPointDailyBO.setProvinceId(DO.getProvinceId());
			reportAreaAdPointDailyBO.setReportTotalPoint(DO.getReportTotalPoint());
			rtnList.add(reportAreaAdPointDailyBO);
		}
		return rtnList;
	}

	@Transactional
	@Override
	public int insertReportAreaAdPointDaily(ReportAreaAdPointDailyParams param) {
		ReportAreaAdPointDailyDO reportAreaAdPointDailyDO = new ReportAreaAdPointDailyDO();
		reportAreaAdPointDailyDO.setAreaId(param.getAreaId());
		reportAreaAdPointDailyDO.setCityId(param.getCityId());
		reportAreaAdPointDailyDO.setGmtCreate(param.getGmtCreate());
		reportAreaAdPointDailyDO.setGmtReport(param.getGmtReport());
		reportAreaAdPointDailyDO.setProvinceId(param.getProvinceId());
		reportAreaAdPointDailyDO.setReportTotalPoint(param.getReportTotalPoint());
		return reportAreaAdPointDailyDOMapper.insertSelective(reportAreaAdPointDailyDO);
	}

	@Transactional
	@Override
	public int deleteReportAreaAdPointDaily(Long id) {
		return reportAreaAdPointDailyDOMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<ReportAreaAdPointMonthBO> selectReportAreaAdPointDailyInMonth(String bdate, String edate) {
		List<ReportAreaAdPointDailyInMonthDOView> viewList = reportAreaAdPointDailyDOMapperExtend.selectReportAreaAdPointDailyInMonth(DateUtil.formatDate(bdate, "yyyy-MM-dd"), DateUtil.formatDate(edate, "yyyy-MM-dd"));
		List<ReportAreaAdPointMonthBO> rtnList = new ArrayList<ReportAreaAdPointMonthBO>();
		if(viewList != null && !viewList.isEmpty()) {
			for(ReportAreaAdPointDailyInMonthDOView view : viewList) {
				ReportAreaAdPointMonthBO reportAreaAdPointMonthBO = new ReportAreaAdPointMonthBO();
				reportAreaAdPointMonthBO.setAreaId(view.getAreaId());
				reportAreaAdPointMonthBO.setCityId(view.getCityId());
				reportAreaAdPointMonthBO.setProvinceId(view.getProvinceId());
				reportAreaAdPointMonthBO.setReportTotalPoint(view.getTotalPoint());
				rtnList.add(reportAreaAdPointMonthBO);
			}
			
		}
		return rtnList;
	}

}
