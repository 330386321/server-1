package com.lawu.eshop.property.srv.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.property.param.ReportAdEarningsPointParam;
import com.lawu.eshop.property.srv.bo.ReportAdEarningsPointBO;
import com.lawu.eshop.property.srv.bo.ReportEarningsBO;
import com.lawu.eshop.property.srv.domain.extend.ReportAdEarningsPointView;
import com.lawu.eshop.property.srv.mapper.extend.TransactionDetailExtendDOMapper;
import com.lawu.eshop.property.srv.service.ReportAdEarningsPointService;

@Service
public class ReportAdEarningsPointServiceImpl implements ReportAdEarningsPointService{
	
	@Autowired
	private TransactionDetailExtendDOMapper transactionDetailExtendDOMapper;

	@Override
	public ReportAdEarningsPointBO getReportAdEarningsPoint(ReportAdEarningsPointParam ReportAdEarningsPointParam) {
		ReportAdEarningsPointView view=new ReportAdEarningsPointView();
		view.setBizId(ReportAdEarningsPointParam.getBizId());
		view.setPointType(ReportAdEarningsPointParam.getMemberTransactionTypeEnum().getValue());
		view.setLoveType(ReportAdEarningsPointParam.getLoveTypeEnum().getValue());
		ReportAdEarningsPointView  viewDate=transactionDetailExtendDOMapper.getReportAdEarningsPoint(view);
		ReportAdEarningsPointView  viewLoveDate=transactionDetailExtendDOMapper.getReportAdEarningsLovePoint(view);
		ReportAdEarningsPointBO bo=new ReportAdEarningsPointBO();
		if(viewDate==null){
			bo.setUserTotalPoint(BigDecimal.valueOf(0));
		}else{
			bo.setUserTotalPoint(viewDate.getUserTotalPoint());
		}
		
		if(viewLoveDate==null){
			bo.setLoveTotalPoint(BigDecimal.valueOf(0));
		}else{
			bo.setLoveTotalPoint(viewLoveDate.getLoveTotalPoint());
		}
		
		return bo;
	}

	@Override
	public ReportEarningsBO getReportEarnings(Long bzId) {
		ReportAdEarningsPointView view=new ReportAdEarningsPointView();
		view.setBizId(bzId);
		ReportAdEarningsPointView  userPoint=transactionDetailExtendDOMapper.getUserPointByBzId(view);
		 
		ReportAdEarningsPointView  lovePoint=transactionDetailExtendDOMapper.getLovePointByBzId(view);
		 
		 ReportEarningsBO bo=new ReportEarningsBO();
		 if(userPoint==null){
			 bo.setUserPoint(new BigDecimal("0"));
		 }else{
			 bo.setUserPoint(userPoint.getUserTotalPoint());
		 }
		 
		 if(lovePoint==null){
			 bo.setLovaPoint(new BigDecimal("0"));
		 }else{
			 bo.setLovaPoint(lovePoint.getLoveTotalPoint());
		 }
		 
		 return bo;
	}

}
