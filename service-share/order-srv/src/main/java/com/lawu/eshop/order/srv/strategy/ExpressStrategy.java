package com.lawu.eshop.order.srv.strategy;

import com.lawu.eshop.order.srv.bo.ExpressInquiriesDetailBO;

/**
 * 快递策略
 * 
 * @author Sunny
 * @date 2017/4/10
 */
public interface ExpressStrategy {
	
	ExpressInquiriesDetailBO inquiries(String expCode, String expNo);
    
}
