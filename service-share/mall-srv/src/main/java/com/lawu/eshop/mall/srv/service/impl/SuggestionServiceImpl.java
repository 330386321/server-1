package com.lawu.eshop.mall.srv.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.mall.param.SuggestionParam;
import com.lawu.eshop.mall.srv.converter.SuggestionConverter;
import com.lawu.eshop.mall.srv.domain.SuggestionDO;
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

	@Transactional
	@Override
	public Integer save(String userNum, SuggestionParam param) {
		SuggestionDO suggestionDO = SuggestionConverter.convert(userNum, param);
		suggestionDO.setGmtCreate(new Date());
		suggestionDO.setGmtModified(new Date());
		
		// 空值交给Mybatis去处理
		int result = suggestionDOMapper.insertSelective(suggestionDO);

		// save返回id出去
		return suggestionDO.getId() != null ? suggestionDO.getId() : result;
	}

}
