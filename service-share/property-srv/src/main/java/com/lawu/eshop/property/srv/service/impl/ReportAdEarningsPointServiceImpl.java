package com.lawu.eshop.property.srv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.property.param.ReportAdEarningsPointParam;
import com.lawu.eshop.property.srv.bo.ReportAdEarningsPointBO;
import com.lawu.eshop.property.srv.domain.extend.ReportAdEarningsPointView;
import com.lawu.eshop.property.srv.mapper.extend.PointDetailDOMapperExtend;
import com.lawu.eshop.property.srv.service.ReportAdEarningsPointService;

@Service
public class ReportAdEarningsPointServiceImpl implements ReportAdEarningsPointService{
	
	@Autowired
	private PointDetailDOMapperExtend pointDetailDOMapperExtend;

	@Override
	public ReportAdEarningsPointBO getReportAdEarningsPoint(ReportAdEarningsPointParam ReportAdEarningsPointParam) {
		ReportAdEarningsPointView view=new ReportAdEarningsPointView();
		view.setBizId(ReportAdEarningsPointParam.getBizId());
		view.setPointType(ReportAdEarningsPointParam.getMemberTransactionTypeEnum().getValue());
		ReportAdEarningsPointView  viewDate=pointDetailDOMapperExtend.getReportAdEarningsPoint(view);
		ReportAdEarningsPointView  viewLoveDate=pointDetailDOMapperExtend.getReportAdEarningsLovePoint(viewDate);
		ReportAdEarningsPointBO bo=new ReportAdEarningsPointBO();
		bo.setUserTotalPoint(viewDate.getUserTotalPoint());
		bo.setLoveTotalPoint(viewLoveDate.getLoveTotalPoint());
		return bo;
	}

}
