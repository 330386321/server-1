package com.lawu.eshop.mall.srv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.mall.srv.bo.ExpressCompanyBO;
import com.lawu.eshop.mall.srv.converter.ExpressCompanyConverter;
import com.lawu.eshop.mall.srv.domain.ExpressCompanyDOExample;
import com.lawu.eshop.mall.srv.mapper.ExpressCompanyDOMapper;
import com.lawu.eshop.mall.srv.service.ExpressCompanyService;

/**
 * 
 * @author Sunny
 * @date 2017/3/27
 */
@Service
public class ExpressCompanyServiceImpl implements ExpressCompanyService {

	@Autowired
	ExpressCompanyDOMapper expressCompanyDOMapper;

	@Override
	public List<ExpressCompanyBO> list() {
		ExpressCompanyDOExample example = new ExpressCompanyDOExample();
		example.createCriteria().andStatusEqualTo((byte) 1);
		example.setOrderByClause("ordinal ASC");

		return ExpressCompanyConverter.convertBOS(expressCompanyDOMapper.selectByExample(example));
	}

}
