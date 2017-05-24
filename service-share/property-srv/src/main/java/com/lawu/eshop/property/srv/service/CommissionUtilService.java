package com.lawu.eshop.property.srv.service;

import java.math.BigDecimal;

import com.lawu.eshop.property.srv.bo.CommissionUtilBO;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年5月24日 上午11:46:58
 *
 */
public interface CommissionUtilService {

	/**
	 * 获取用户点击广告，自己的收益余额与爱心账户
	 * @param clickMoney
	 * @return
	 * @author yangqh
	 * @date 2017年5月24日 上午11:52:31
	 */
	CommissionUtilBO getClickAdMine(BigDecimal clickMoney);
}
