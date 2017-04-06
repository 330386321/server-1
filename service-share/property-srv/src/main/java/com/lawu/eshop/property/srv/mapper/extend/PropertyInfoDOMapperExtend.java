package com.lawu.eshop.property.srv.mapper.extend;

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
	
   
}