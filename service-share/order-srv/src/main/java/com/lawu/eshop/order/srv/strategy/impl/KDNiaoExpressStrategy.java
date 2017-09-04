package com.lawu.eshop.order.srv.strategy.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.converter.ExpressInquiriesDetailConverter;
import com.lawu.eshop.order.srv.strategy.ExpressStrategy;
import com.lawu.eshop.order.srv.utils.express.kdniao.KdniaoTrackQueryAPI;
import com.lawu.eshop.order.srv.utils.express.kdniao.bo.ExpressInquiriesDetail;

@Service("kDNiaoExpressStrategy")
public class KDNiaoExpressStrategy implements ExpressStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(KDNiaoExpressStrategy.class);
	
	@Autowired
	KdniaoTrackQueryAPI api;
	
	/**
	 * 即时查询
	 */
	@Override
	public ExpressInquiriesDetailBO inquiries(String expCode, String expNo) {
		ExpressInquiriesDetailBO expressInquiriesDetailDTO = null;
		try {
			String result = api.orderTraces(expCode, expNo);
			
			// JSON转换成JOPO
			ExpressInquiriesDetail expressInquiriesDetail = JSONObject.parseObject(result, ExpressInquiriesDetail.class);
			
			// Trace按照时间倒序排序
			Collections.reverse(expressInquiriesDetail.getTraces());
			
			expressInquiriesDetailDTO = ExpressInquiriesDetailConverter.convert(expressInquiriesDetail);
		} catch (Exception e) {
			logger.error("查询物流异常", e);
		}
		return expressInquiriesDetailDTO;
	}
	
	/**
	 * 识别快递单号
	 */
	@Override
	public ExpressInquiriesDetailBO recognition(String expNo) {
		ExpressInquiriesDetailBO expressInquiriesDetailDTO = null;
		try {
			String result = api.recognition(expNo);
		} catch (Exception e) {
			logger.error("查询物流异常", e);
		}
		return expressInquiriesDetailDTO;
	}

}