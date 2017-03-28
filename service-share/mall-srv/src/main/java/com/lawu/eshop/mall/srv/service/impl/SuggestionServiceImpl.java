package com.lawu.eshop.mall.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.SuggestionParam;
import com.lawu.eshop.mall.param.SuggestionQueryParam;
import com.lawu.eshop.mall.srv.bo.SuggestionBO;
import com.lawu.eshop.mall.srv.converter.SuggestionConverter;
import com.lawu.eshop.mall.srv.domain.SuggestionDO;
import com.lawu.eshop.mall.srv.domain.SuggestionDOExample;
import com.lawu.eshop.mall.srv.domain.SuggestionDOExample.Criteria;
import com.lawu.eshop.mall.srv.mapper.SuggestionDOMapper;
import com.lawu.eshop.mall.srv.service.SuggestionService;

/**
 * 反馈意见服务实现
 *
 * @author Sunny
 * @date 2017/3/24
 */
@Service
public class SuggestionServiceImpl implements SuggestionService {

	@Autowired
	private SuggestionDOMapper suggestionDOMapper;

	@Override
	public Page<SuggestionBO> page(SuggestionQueryParam query) {

		Page<SuggestionBO> page = new Page<SuggestionBO>();
		Integer count = count(query);
		page.setTotalCount(count);
		if (count == null || count <= 0) {
			return page;
		}
		// 如果count小于0，不再查询records
		page.setRecords(list(query, true));
		return page;
	}

	@Override
	public Integer count(SuggestionQueryParam query) {

		SuggestionDOExample example = new SuggestionDOExample();

		Criteria criteria = example.createCriteria();

		if (query.getClientType() != null) {
			criteria.andClientTypeEqualTo(query.getClientType());
		}

		if (StringUtils.isNotEmpty(query.getContent())) {
			criteria.andContentLike(query.getContent());
		}

		if (StringUtils.isNotEmpty(query.getUserNum())) {
			criteria.andUserNumEqualTo(query.getUserNum());
		}

		if (query.getUserType() != null) {
			criteria.andUserTypeEqualTo(query.getUserType());
		}

		return suggestionDOMapper.countByExample(example);
	}

	@Override
	public List<SuggestionBO> list(SuggestionQueryParam query, boolean isPage) {

		SuggestionDOExample example = new SuggestionDOExample();

		Criteria criteria = example.createCriteria();

		if (query.getClientType() != null) {
			criteria.andClientTypeEqualTo(query.getClientType());
		}

		if (StringUtils.isNotEmpty(query.getContent())) {
			criteria.andContentLike(query.getContent());
		}

		if (StringUtils.isNotEmpty(query.getUserNum())) {
			criteria.andUserNumEqualTo(query.getUserNum());
		}

		if (query.getUserType() != null) {
			criteria.andUserTypeEqualTo(query.getUserType());
		}
		
		if(StringUtils.isNotEmpty(query.getOrderField())){
			example.setOrderByClause(query.getOrderField() + " " + query.getOrderType().name());
		}
		
		if (isPage) {
			RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
			return SuggestionConverter.convertBOS(suggestionDOMapper.selectByExampleWithRowbounds(example, rowBounds));
		}

		return SuggestionConverter.convertBOS(suggestionDOMapper.selectByExample(example));
	}

	@Override
	public SuggestionBO get(Integer id) {
		if (id == null) {
			return null;
		}

		return SuggestionConverter.convert(suggestionDOMapper.selectByPrimaryKey(id));
	}

	@Transactional
	@Override
	public Integer save(SuggestionParam param) {
		SuggestionDO suggestionDO = SuggestionConverter.convert(param);
		suggestionDO.setGmtCreate(new Date());
		suggestionDO.setGmtModified(new Date());
		
		// 空值交给Mybatis去处理
		int result = suggestionDOMapper.insertSelective(suggestionDO);

		// save返回id出去
		return suggestionDO.getId() != null ? suggestionDO.getId() : result;
	}

	@Transactional
	@Override
	public Integer update(SuggestionParam param, Integer id) {
		SuggestionDO suggestionDO = SuggestionConverter.convert(param);
		suggestionDO.setId(id);
		suggestionDO.setGmtModified(new Date());

		// 空值交给Mybatis去处理
		return suggestionDOMapper.updateByPrimaryKeySelective(suggestionDO);
	}

	@Transactional
	@Override
	public Integer remove(Integer id) {
		if (id == null) {
			return null;
		}

		int i = suggestionDOMapper.deleteByPrimaryKey(id);

		return i;
	}
}
