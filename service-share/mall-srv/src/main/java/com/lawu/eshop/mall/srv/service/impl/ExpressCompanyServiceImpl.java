package com.lawu.eshop.mall.srv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.mall.constants.StatusEnum;
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
	
	/**
	 * 查询全部快递公司，根据ordinal排序
	 * 
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月10日
	 */
	@Override
	public List<ExpressCompanyBO> list() {
		return list(null);
	}
	
	/**
	 * 查询快递公司，根据ordinal排序
	 * 
	 * @return
	 * @author jiangxinjun
	 * @date 2017年7月10日
	 */
	@Override
	public List<ExpressCompanyBO> list(Boolean isShow) {
		ExpressCompanyDOExample example = new ExpressCompanyDOExample();
		ExpressCompanyDOExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(StatusEnum.STATUS_VALID.val);
		if (isShow != null) {
			criteria.andIsShowEqualTo(isShow);
		}
		example.setOrderByClause("ordinal ASC");
		return ExpressCompanyConverter.convertBOS(expressCompanyDOMapper.selectByExample(example));
	}
	
	/**
	 * 根据快递公司id查询快递公司
	 * 
	 * @param id
	 *            快递公司id
	 * @return
	 */
	@Override
	public ExpressCompanyBO get(Integer id) {
		return ExpressCompanyConverter.convert(expressCompanyDOMapper.selectByPrimaryKey(id));
	}
	
}
