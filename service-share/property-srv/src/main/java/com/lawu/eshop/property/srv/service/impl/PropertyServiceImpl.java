package com.lawu.eshop.property.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.property.param.TestQueryParam;
import com.lawu.eshop.property.srv.bo.QueryPropertyBO;
import com.lawu.eshop.property.srv.bo.WithdrawCashBackageQueryBO;
import com.lawu.eshop.property.srv.domain.PropertyDO;
import com.lawu.eshop.property.srv.domain.PropertyDOExample;
import com.lawu.eshop.property.srv.domain.WithdrawCashDO;
import com.lawu.eshop.property.srv.mapper.PropertyDOMapper;
import com.lawu.eshop.property.srv.service.PropertyService;
import com.lawu.eshop.utils.BeanUtil;


@Service
public class PropertyServiceImpl implements PropertyService {
	
	@Autowired
	private PropertyDOMapper propertyDOMapper;

	@Override
	public String getValue(String key) {
		PropertyDOExample example = new PropertyDOExample();
		example.createCriteria().andNameEqualTo(key);
		List<PropertyDO> list = propertyDOMapper.selectByExample(example);
		if(list == null || list.isEmpty()){
			return "";
		}
		return list.get(0).getValue();
	}

	@Override
	public List<String> getValues(String key) {
		PropertyDOExample example = new PropertyDOExample();
		example.createCriteria().andNameEqualTo(key);
		List<PropertyDO> list = propertyDOMapper.selectByExample(example);
		if(list == null || list.isEmpty()){
			return null;
		}
		List<String> values = new ArrayList<String>();
		for(PropertyDO pdo : list){
			values.add(pdo.getValue());
		}
		return values;
	}

	@Override
	public Page<QueryPropertyBO> query(TestQueryParam param) throws Exception {
		PropertyDOExample example = new PropertyDOExample();
		if(param.getName() != null && !"".equals(param.getName())){
			example.createCriteria().andNameLike("%"+param.getName()+"%");			
		}
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		Page<QueryPropertyBO> page = new Page<QueryPropertyBO>();
		page.setTotalCount(propertyDOMapper.countByExample(example));
		page.setCurrentPage(param.getCurrentPage());
		List<PropertyDO> listDOS = propertyDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		List<QueryPropertyBO> bos = new ArrayList<QueryPropertyBO>();
		for(PropertyDO pdo : listDOS){
			QueryPropertyBO bo =  new QueryPropertyBO();
			BeanUtil.copyProperties(pdo, bo);
			bos.add(bo);
		}
		page.setRecords(bos);
		return page;
	}

}
