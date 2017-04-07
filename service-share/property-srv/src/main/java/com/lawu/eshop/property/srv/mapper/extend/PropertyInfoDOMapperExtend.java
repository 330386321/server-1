package com.lawu.eshop.property.srv.mapper.extend;

import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOEiditView;
import com.lawu.eshop.property.srv.domain.extend.PropertyInfoDOView;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月6日 上午9:56:27
 *
 */
public interface PropertyInfoDOMapperExtend {
	
	int updateByPrimaryKeySelective(PropertyInfoDOView record);

	/**
	 * 加积分
	 * @param editView
	 */
	void updatePropertyInfoAddPoint(PropertyInfoDOEiditView editView);

	/**
	 * 减积分
	 * @param editView
	 */
	void updatePropertyInfoMinusPoint(PropertyInfoDOEiditView editView);

	/**
	 * 加余额
	 * @param editView
	 */
	void updatePropertyInfoAddBalance(PropertyInfoDOEiditView editView);

	/**
	 * 减余额
	 * @param editView
	 */
	void updatePropertyInfoMinusBalance(PropertyInfoDOEiditView editView);

	/**
	 * 加爱心账户
	 * @param editView
	 */
	void updatePropertyInfoAddLove(PropertyInfoDOEiditView editView);

	/**
	 * 减爱心账户
	 * @param editView
	 */
	void updatePropertyInfoMinusLove(PropertyInfoDOEiditView editView);
	
   
}