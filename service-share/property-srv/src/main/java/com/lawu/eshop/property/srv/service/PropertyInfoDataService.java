package com.lawu.eshop.property.srv.service;

import com.lawu.eshop.property.param.PropertyInfoDataParam;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月12日 下午12:53:13
 *
 */
public interface PropertyInfoDataService {

	/**
	 * 业务消费减积分
	 * @param param
	 * @return
	 */
	int doHanlderMinusPoint(PropertyInfoDataParam param);
	
	/**
	 * 业务消费加积分
	 * @param param
	 * @return
	 */
	int doHanlderAddPoint(PropertyInfoDataParam param);
	
}
