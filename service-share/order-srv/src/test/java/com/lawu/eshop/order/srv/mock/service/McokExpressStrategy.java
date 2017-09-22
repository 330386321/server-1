package com.lawu.eshop.order.srv.mock.service;

import java.util.ArrayList;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.lawu.eshop.order.constants.ExpressInquiriesDetailStateEnum;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ExpressRecognitionDetailBO;
import com.lawu.eshop.order.srv.bo.ShipperBO;
import com.lawu.eshop.order.srv.bo.TraceBO;
import com.lawu.eshop.order.srv.strategy.ExpressStrategy;
import com.lawu.eshop.order.srv.utils.express.kdniao.constants.CodeEnum;

@Primary
@Service
public class McokExpressStrategy implements ExpressStrategy {
	
	@Override
	public ExpressInquiriesDetailBO inquiries(String expCode, String expNo) {
		ExpressInquiriesDetailBO rtn = new ExpressInquiriesDetailBO();
		rtn.setState(ExpressInquiriesDetailStateEnum.ON_THE_WAY);
		rtn.setShipperCode("SF");
		rtn.setTraces(new ArrayList<>());
		TraceBO traceBO = new TraceBO();
		traceBO.setAcceptStation("等待快递员上门揽件");
		traceBO.setAcceptTime("2017-08-30 11:44:00");
		rtn.getTraces().add(traceBO);
		
		return rtn;
	}

	@Override
	public ExpressRecognitionDetailBO recognition(String expNo) {
		ExpressRecognitionDetailBO rtn = new ExpressRecognitionDetailBO();
		rtn.setSuccess(true);
		rtn.seteBusinessId("123456");
		rtn.setLogisticCode(expNo);
		rtn.setCode(CodeEnum.SUCCESS);
		rtn.setShippers(new ArrayList<>());
		ShipperBO shipperBO = new ShipperBO();
		shipperBO.setShipperCode("SF");
		shipperBO.setShipperName("顺丰");
		rtn.getShippers().add(shipperBO);
		return rtn;
	}

}