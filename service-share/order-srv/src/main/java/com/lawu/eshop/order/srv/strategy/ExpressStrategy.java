package com.lawu.eshop.order.srv.strategy;

import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;

/**
 * 快递策略
 * 
 * @author Sunny
 * @date 2017/4/10
 */
public interface ExpressStrategy {
	
	/**
	 * 查询物流轨迹
	 * @param expCode
	 * @param expNo
	 * @return
	 * @author jiangxinjun
	 * @date 2017年9月4日
	 */
	ExpressInquiriesDetailBO inquiries(String expCode, String expNo);
	
	/**
	 * 识别快递单号
	 * @param expNo
	 * @return
	 * @author jiangxinjun
	 * @date 2017年9月4日
	 */
	ExpressInquiriesDetailBO recognition(String expNo);
}
