package com.lawu.eshop.ad.srv.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.srv.bo.ReportEarningsBO;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.AdDOExample;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.ad.srv.mapper.extend.MemberAdRecordDOMapperExtend;
import com.lawu.eshop.ad.srv.mapper.extend.PointPoolDOMapperExtend;
import com.lawu.eshop.ad.srv.service.ReportEarningsService;
import com.lawu.eshop.utils.DateUtil;

@Service
public class ReportEarningsServiceImpl implements ReportEarningsService {
	
	
	@Autowired
	private PointPoolDOMapperExtend pointPoolDOMapperExtend;
	
	@Autowired
	private MemberAdRecordDOMapperExtend memberAdRecordDOMapperExtend;
	
	@Autowired
	private AdDOMapper adDOMapper;
	
	

	@Override
	public List<ReportEarningsBO> getReportEarnings() {
		
		AdDOExample adDOExample=new AdDOExample();
		
		Date begin = DateUtil.formatDate(new Date()+" 00:00:00","yyyy-MM-dd HH:mm:ss");
		Date end = DateUtil.formatDate(new Date()+" 23:59:59","yyyy-MM-dd HH:mm:ss");
		
		adDOExample.createCriteria().andStatusBetween(AdStatusEnum.AD_STATUS_PUTED.val, AdStatusEnum.AD_STATUS_OUT.val)
					.andGmtModifiedBetween(begin, end);
		
		List<AdDO> list=adDOMapper.selectByExample(adDOExample);
		
		
		List<ReportEarningsBO> listBO=new ArrayList<>();
		
		for (AdDO adDO : list) {
			
			ReportEarningsBO bo=new ReportEarningsBO();
			bo.setId(adDO.getId());
			
			//平面和视频
			if(adDO.getType()==AdTypeEnum.AD_TYPE_FLAT.val || adDO.getType()==AdTypeEnum.AD_TYPE_VIDEO.val){
				
				 BigDecimal adClickPoint=  memberAdRecordDOMapperExtend.getTotlePoint(adDO.getId());
				 bo.setAdPoint(adClickPoint);
				 
			}else{ //红包和抢赞
				 
				 BigDecimal adPraisePoint= pointPoolDOMapperExtend.getTotlePoint(adDO.getId());
				 bo.setAdPoint(adPraisePoint);
			}
			
			listBO.add(bo);
			
		}
		
		return listBO;
	}



	@Override
	public List<ReportEarningsBO> getReportMonthEarnings() {
		AdDOExample adDOExample=new AdDOExample();
		
		Date begin = DateUtil.formatDate(new Date()+" 00:00:00","yyyy-MM-dd HH:mm:ss");
		Date end = DateUtil.formatDate(new Date()+" 23:59:59","yyyy-MM-dd HH:mm:ss");
		
		adDOExample.createCriteria().andStatusBetween(AdStatusEnum.AD_STATUS_PUTED.val, AdStatusEnum.AD_STATUS_OUT.val)
					.andGmtModifiedBetween(begin, end);
		
		List<AdDO> list=adDOMapper.selectByExample(adDOExample);
		
		
		List<ReportEarningsBO> listBO=new ArrayList<>();
		
		for (AdDO adDO : list) {
			
			ReportEarningsBO bo=new ReportEarningsBO();
			bo.setId(adDO.getId());
			
			//平面和视频
			if(adDO.getType()==AdTypeEnum.AD_TYPE_FLAT.val || adDO.getType()==AdTypeEnum.AD_TYPE_VIDEO.val){
				
				 BigDecimal adClickPoint=  memberAdRecordDOMapperExtend.getTotlePoint(adDO.getId());
				 bo.setAdPoint(adClickPoint);
				 
			}else{ //红包和抢赞
				 
				 BigDecimal adPraisePoint= pointPoolDOMapperExtend.getTotlePoint(adDO.getId());
				 bo.setAdPoint(adPraisePoint);
			}
			
			listBO.add(bo);
			
		}
		
		return listBO;
	}

} 
