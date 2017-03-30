package com.lawu.eshop.order.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.mall.param.ShoppingCartParam;
import com.lawu.eshop.order.srv.bo.ShoppingCartBO;
import com.lawu.eshop.order.srv.converter.ShoppingCartConverter;
import com.lawu.eshop.order.srv.domain.ShoppingCartDO;
import com.lawu.eshop.order.srv.domain.ShoppingCartDOExample;
import com.lawu.eshop.order.srv.mapper.ShoppingCartDOMapper;
import com.lawu.eshop.order.srv.service.ShoppingCartService;

/**
 * 反馈意见服务实现
 *
 * @author Sunny
 * @date 2017/3/24
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ShoppingCartDOMapper shoppingCartDOMapper;

	@Override
	public List<ShoppingCartBO> findListByMemberId(Long memberId) {
		ShoppingCartDOExample example = new ShoppingCartDOExample();

		if (memberId == null) {
			return null;
		}

		example.createCriteria().andMemberIdEqualTo(memberId);
		return ShoppingCartConverter.convertBOS(shoppingCartDOMapper.selectByExample(example));
	}
	
	@Transactional
	@Override
	public Long save(Long memberId, ShoppingCartParam param) {
		ShoppingCartDO suggestionDO = ShoppingCartConverter.convert(param);
		suggestionDO.setMemberId(memberId);
		suggestionDO.setGmtCreate(new Date());
		suggestionDO.setGmtModified(new Date());

		// 空值交给Mybatis去处理
		Long result = (long) shoppingCartDOMapper.insertSelective(suggestionDO);

		// save返回id出去
		return suggestionDO.getId() != null ? suggestionDO.getId() : result;
	}
	
	@Transactional
	@Override
	public Integer update(Long id, ShoppingCartParam param) {
		ShoppingCartDO suggestionDO = ShoppingCartConverter.convert(param);
		suggestionDO.setId(id);
		suggestionDO.setGmtModified(new Date());
		
		// 空值交给Mybatis去处理
		return shoppingCartDOMapper.updateByPrimaryKeySelective(suggestionDO);
	}

	@Transactional
	@Override
	public Integer remove(Long id) {
		return shoppingCartDOMapper.deleteByPrimaryKey(id);
	}
	
	public ShoppingCartDO get(Long id){
		return shoppingCartDOMapper.selectByPrimaryKey(id);
	}
}
