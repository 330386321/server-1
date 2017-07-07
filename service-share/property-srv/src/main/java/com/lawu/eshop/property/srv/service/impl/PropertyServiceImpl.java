package com.lawu.eshop.property.srv.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.constants.PropertyType;
import com.lawu.eshop.property.param.TestQuery1Param;
import com.lawu.eshop.property.param.TestQueryParam;
import com.lawu.eshop.property.srv.bo.QueryPropertyBO;
import com.lawu.eshop.property.srv.domain.PropertyDO;
import com.lawu.eshop.property.srv.domain.PropertyDOExample;
import com.lawu.eshop.property.srv.domain.PropertyDOExample.Criteria;
import com.lawu.eshop.property.srv.mapper.PropertyDOMapper;
import com.lawu.eshop.property.srv.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	private PropertyDOMapper propertyDOMapper;

	@Override
	public String getValue(String key) {
		PropertyDOExample example = new PropertyDOExample();
		example.createCriteria().andNameEqualTo(key);
		List<PropertyDO> list = propertyDOMapper.selectByExample(example);
		if (list == null || list.isEmpty()) {
			String defaultValue = "";

			switch (key) {
			case PropertyType.CASH_SCALE:
				defaultValue = PropertyType.CASH_SCALE_DEFAULT;
				break;
			case PropertyType.CASH_GREATER_ONE_MINUS_MONEY:
				defaultValue = PropertyType.CASH_GREATER_ONE_MINUS_MONEY_DEFAULT;
				break;
			case PropertyType.CASH_MIN_MONEY:
				defaultValue = PropertyType.CASH_MIN_MONEY_DEFAULT;
				break;
			case PropertyType.MEMBER_THIRD_PAY_POINT:
				defaultValue = PropertyType.MEMBER_THIRD_PAY_POINT_DEFAULT;
				break;
			case PropertyType.MERCHANT_BONT:
				defaultValue = PropertyType.MERCHANT_BONT_DEFAULT;
				break;
			case PropertyType.PRODUCT_ORDER_MONEY_FREEZE_DAYS:
				defaultValue = PropertyType.PRODUCT_ORDER_MONEY_FREEZE_DAYS_DEFAULT;
				break;
			case PropertyType.DEPOSIT_REFUND_DIFF_DAYS:
				defaultValue = PropertyType.DEPOSIT_REFUND_DIFF_DAYS_DEFAULT;
				break;
			case PropertyType.ad_commission_0:
				defaultValue = PropertyType.ad_commission_0_default;
				break;
			case PropertyType.ad_commission_1:
				defaultValue = PropertyType.ad_commission_1_default;
				break;
			case PropertyType.ad_commission_2:
				defaultValue = PropertyType.ad_commission_2_default;
				break;
			case PropertyType.ad_commission_3:
				defaultValue = PropertyType.ad_commission_3_default;
				break;
			case PropertyType.love_account_scale:
				defaultValue = PropertyType.love_account_scale_default;
				break;
			case PropertyType.sale_commission_1:
				defaultValue = PropertyType.sale_commission_1_default;
				break;
			case PropertyType.sale_commission_2:
				defaultValue = PropertyType.sale_commission_2_default;
				break;
			case PropertyType.sale_commission_3:
				defaultValue = PropertyType.sale_commission_3_default;
				break;
			case PropertyType.sale_commission_add_scope:
				defaultValue = PropertyType.sale_commission_add_scope_default;
				break;
			default:
				break;
			}
			return defaultValue;
		}
		return list.get(0).getValue();
	}

	@Override
	public List<String> getValues(String key) {
		PropertyDOExample example = new PropertyDOExample();
		example.createCriteria().andNameEqualTo(key);
		List<PropertyDO> list = propertyDOMapper.selectByExample(example);
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<String> values = new ArrayList<String>();
		for (PropertyDO pdo : list) {
			values.add(pdo.getValue());
		}
		return values;
	}

	@Override
	public Page<QueryPropertyBO> query(TestQuery1Param param) {
		PropertyDOExample example = new PropertyDOExample();
		Criteria criteria = example.createCriteria();
		if (param.getName() != null && !"".equals(param.getName())) {
			criteria.andNameLike("%" + param.getName() + "%");
		}
		if (StringUtils.isNotBlank(param.getSortName()) && StringUtils.isNotBlank(param.getSortOrder())) {
			example.setOrderByClause(param.getSortName() + " " + param.getSortOrder());
		}

		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		Page<QueryPropertyBO> page = new Page<QueryPropertyBO>();
		page.setTotalCount(propertyDOMapper.countByExample(example));
		page.setCurrentPage(param.getCurrentPage());
		List<PropertyDO> listDOS = propertyDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		List<QueryPropertyBO> bos = new ArrayList<QueryPropertyBO>();
		for (PropertyDO pdo : listDOS) {
			QueryPropertyBO bo = new QueryPropertyBO();
			bo.setId(pdo.getId());
			bo.setName(pdo.getName());
			bo.setValue(pdo.getValue());
			bo.setRemark(pdo.getRemark());
			bos.add(bo);
		}
		page.setRecords(bos);
		return page;
	}

	@Override
	@Transactional
	public int save(TestQueryParam param) {
		PropertyDO pdo = new PropertyDO();
		pdo.setName(param.getName());
		pdo.setValue(param.getValue());
		pdo.setRemark(param.getRemark());
		if(param.getId() == null || param.getId() == 0L){
			pdo.setGmtCreate(new Date());
			propertyDOMapper.insertSelective(pdo);
		}else{
			pdo.setGmtModified(new Date());
			PropertyDOExample example = new PropertyDOExample();
			example.createCriteria().andIdEqualTo(param.getId());
			propertyDOMapper.updateByExampleSelective(pdo, example);
		}
		return ResultCode.SUCCESS;
	}

	@Override
	@Transactional
	public int delete(String propertyIds) {
		String propertyIdss[] = propertyIds.split(",");
		List<String> list = Arrays.asList(propertyIdss);
		for(String id : list){
			propertyDOMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		return ResultCode.SUCCESS;
	}

	@Override
	public QueryPropertyBO get(Long propertyId) {
		PropertyDO pdo = propertyDOMapper.selectByPrimaryKey(propertyId);
		QueryPropertyBO bo = new QueryPropertyBO();
		bo.setId(pdo.getId());
		bo.setName(pdo.getName());
		bo.setValue(pdo.getValue());
		bo.setRemark(pdo.getRemark());
		return bo;
	}

}
