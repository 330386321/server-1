package com.lawu.eshop.statistics.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.statistics.param.ReportAdEarningsParam;
import com.lawu.eshop.statistics.param.ReportAdEarningsQueryParam;
import com.lawu.eshop.statistics.srv.bo.ReportAdEarningsBO;
import com.lawu.eshop.statistics.srv.converter.ReportAdEarningsConverter;
import com.lawu.eshop.statistics.srv.domain.ReportAdEarningsDO;
import com.lawu.eshop.statistics.srv.domain.ReportAdEarningsDOExample;
import com.lawu.eshop.statistics.srv.mapper.ReportAdEarningsDOMapper;
import com.lawu.eshop.statistics.srv.mapper.extend.ReportAdEarningsDOMapperExtend;
import com.lawu.eshop.statistics.srv.service.ReportAdEarningsService;

/**
 * 广告收益统计接口实现类
 * @author zhangrc
 * @date 2017/06/29
 *
 */
@Service
public class ReportAdEarningsServiceImpl implements ReportAdEarningsService {
	
	@Autowired 
	private ReportAdEarningsDOMapper ReportAdEarningsDOMapper;
	
	@Autowired 
	private ReportAdEarningsDOMapperExtend reportAdEarningsDOMapperExtend;

	
	@Override
	@Transactional
	public void saveReportAdEarnings(ReportAdEarningsParam reportAdEarningsParam) {
		ReportAdEarningsDO reportAdEarningsDO=new ReportAdEarningsDO();
		reportAdEarningsDO.setAdId(reportAdEarningsParam.getAdId());
		reportAdEarningsDO.setAdStatus(reportAdEarningsParam.getAdStatusEnum().val);
		reportAdEarningsDO.setAdTitle(reportAdEarningsParam.getAdTitle());
		reportAdEarningsDO.setAdCreateTime(reportAdEarningsParam.getAdCreateTime());
		reportAdEarningsDO.setAdTotalPoint(reportAdEarningsParam.getAdTotalPoint());
		reportAdEarningsDO.setAdType(reportAdEarningsParam.getAdTypeEnum().val);
		reportAdEarningsDO.setMerchantNum(reportAdEarningsParam.getMerchantNum());
		reportAdEarningsDO.setMerchantName(reportAdEarningsParam.getMerchantNum());
		reportAdEarningsDO.setLoveTotalPoint(reportAdEarningsParam.getLoveTotalPoint());
		reportAdEarningsDO.setUserTotalPoint(reportAdEarningsParam.getUserTotalPoint());
		reportAdEarningsDO.setGmtCreate(new Date());
		reportAdEarningsDO.setStatus(reportAdEarningsParam.getReportAdEarningsStatusEnum().val);
		reportAdEarningsDO.setGmtModified(new Date());
		ReportAdEarningsDOMapper.insertSelective(reportAdEarningsDO);
		
		
		ReportAdEarningsDOExample example =new ReportAdEarningsDOExample();
		example.createCriteria().andAdIdEqualTo(reportAdEarningsParam.getAdId());
		Long count=ReportAdEarningsDOMapper.countByExample(example);
		
		if(count.intValue()>0){
			reportAdEarningsDO.setGmtModified(new Date());
			ReportAdEarningsDOMapper.updateByExampleSelective(reportAdEarningsDO, example);
		}else{
			reportAdEarningsDO.setGmtModified(new Date());
			ReportAdEarningsDOMapper.insertSelective(reportAdEarningsDO);
		}
	}

	@Override
	public Page<ReportAdEarningsBO> selectReportAdEarnings(ReportAdEarningsQueryParam query) {
		ReportAdEarningsDOExample example =new ReportAdEarningsDOExample();
		if(query.getAdTitle()!=null){
			example.createCriteria().andAdTitleLike("%" + query.getAdTitle() + "%");
		}else if(query.getMerchantNum()!=null){
			example.createCriteria().andMerchantNumEqualTo(query.getMerchantNum());
		}else if(query.getAdStatusEnum()!=null){
			example.createCriteria().andAdStatusEqualTo(query.getAdStatusEnum().val);
		}else if(query.getBeginTime()!=null && query.getEndTime()!=null){
			example.createCriteria().andGmtCreateBetween(query.getBeginTime(), query.getEndTime());
		}
		Long count=ReportAdEarningsDOMapper.countByExample(example);
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<ReportAdEarningsDO>  list=ReportAdEarningsDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		List<ReportAdEarningsBO>  listBO=new ArrayList<>();
		for (ReportAdEarningsDO reportAdEarningsDO : list) {
			listBO.add(ReportAdEarningsConverter.convertBO(reportAdEarningsDO));
		}
		Page<ReportAdEarningsBO> page=new Page<>();
		page.setCurrentPage(query.getCurrentPage());
		page.setTotalCount(count.intValue());
		page.setRecords(listBO);
		return page;
	}



	@Override
	public List<Long> getReportAdEarningsIds() {
		
		 List<Long> ids=reportAdEarningsDOMapperExtend.getReportAdEarningsIds();
		
		return ids;
	}

}
