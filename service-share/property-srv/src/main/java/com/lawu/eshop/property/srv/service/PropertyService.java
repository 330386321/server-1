package com.lawu.eshop.property.srv.service;

import java.util.List;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月5日 下午6:49:51
 *
 */
public interface PropertyService {
   
	String getValue(String key);
	
	List<String> getValues(String key);
}
