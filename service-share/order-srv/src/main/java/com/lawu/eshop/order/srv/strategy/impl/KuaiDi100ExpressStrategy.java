package com.lawu.eshop.order.srv.strategy.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;
import com.lawu.eshop.order.srv.bo.ExpressRecognitionDetailBO;
import com.lawu.eshop.order.srv.converter.ExpressConverter;
import com.lawu.eshop.order.srv.strategy.ExpressStrategy;
import com.lawu.eshop.order.srv.utils.express.kuaidi100.KuaiDi100Api;
import com.lawu.eshop.order.srv.utils.express.kuaidi100.bo.ExpressInquiriesDetail;
import com.lawu.eshop.order.srv.utils.express.kuaidi100.constants.StatusEnum;

@Primary
@Service("kuaiDi100ExpressStrategy")
public class KuaiDi100ExpressStrategy implements ExpressStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(KuaiDi100ExpressStrategy.class);
	
	@Autowired
	private KuaiDi100Api api;
	
	/**
	 * 即时查询
	 */
	@Override
	public ExpressInquiriesDetailBO inquiries(String expCode, String expNo) {
		ExpressInquiriesDetailBO rtn = null;
		try {
			String result = api.orderTraces(expCode, expNo);
			
			// JSON转换成JOPO
			ExpressInquiriesDetail expressInquiriesDetail = JSONObject.parseObject(result, ExpressInquiriesDetail.class);
			
			if (expressInquiriesDetail.getStatus().equals(StatusEnum.INTERFACE_EXCEPTION.getValue())) {
				logger.error("快递查询接口返回异常");
				logger.error("Result:{}", result);
				logger.error("Result:{}", expressInquiriesDetail.getMessage());
			}
			
			rtn = ExpressConverter.convert(expressInquiriesDetail);
		} catch (Exception e) {
			logger.error("快递查询异常", e);
		}
		return rtn;
	}
	
	/**
	 * 识别快递单号
	 */
	@Override
	public ExpressRecognitionDetailBO recognition(String expNo) {
		return null;
	}

}