package com.lawu.eshop.property.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.param.TestQuery1Param;
import com.lawu.eshop.property.param.TestQueryParam;
import com.lawu.eshop.property.srv.bo.QueryPropertyBO;

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

	Page<QueryPropertyBO> query(TestQuery1Param param);

	int save(TestQueryParam param);

	int delete(String propertyIds);

	QueryPropertyBO get(Long propertyId);
}
