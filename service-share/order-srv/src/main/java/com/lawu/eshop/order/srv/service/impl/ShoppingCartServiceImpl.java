package com.lawu.eshop.order.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.order.param.ShoppingCartSaveParam;
import com.lawu.eshop.order.param.ShoppingCartUpdateParam;
import com.lawu.eshop.order.srv.bo.ShoppingCartBO;
import com.lawu.eshop.order.srv.converter.ShoppingCartConverter;
import com.lawu.eshop.order.srv.domain.ShoppingCartDO;
import com.lawu.eshop.order.srv.domain.ShoppingCartDOExample;
import com.lawu.eshop.order.srv.mapper.ShoppingCartDOMapper;
import com.lawu.eshop.order.srv.service.ShoppingCartService;

/**
 * 购物车服务实现
 *
 * @author Sunny
 * @date 2017/3/24
 */
@Service
public class ShoppingCartServiceImpl extends BaseController implements ShoppingCartService {

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

	/**
	 * 加入购物车
	 * 
	 * @param memberId 会员id
	 * @param param 保存参数
	 * @return
	 * @author Sunny
	 */
	@Transactional
	@Override
	public Result<Long> save(Long memberId, ShoppingCartSaveParam param) {
		ShoppingCartDO suggestionDO = ShoppingCartConverter.convert(param);
		suggestionDO.setMemberId(memberId);
		suggestionDO.setGmtCreate(new Date());
		suggestionDO.setGmtModified(new Date());

		// 空值交给Mybatis去处理
		int result = shoppingCartDOMapper.insertSelective(suggestionDO);

		if (result <= 0) {
			return successCreated(ResultCode.SAVE_FAIL);
		}

		return successCreated(suggestionDO.getId());
	}

	/**
	 * 根据id更新购物车
	 * 
	 * @param id
	 *            购物车id
	 * @param memberId
	 *            会员id
	 * @param param
	 *            更新参数
	 * @return
	 * @author Sunny
	 */
	@Transactional
	@Override
	public int update(Long id, Long memberId, ShoppingCartUpdateParam param) {

		ShoppingCartDO shoppingCartDO = shoppingCartDOMapper.selectByPrimaryKey(id);

		if (shoppingCartDO == null || shoppingCartDO.getId() == null || shoppingCartDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}

		// 验证此条记录是否属于当前用户
		if (!shoppingCartDO.getMemberId().equals(memberId)) {
			return ResultCode.ILLEGAL_OPERATION;
		}

		ShoppingCartDO suggestionDO = ShoppingCartConverter.convert(param);
		suggestionDO.setId(id);
		suggestionDO.setGmtModified(new Date());

		// 空值交给Mybatis去处理
		shoppingCartDOMapper.updateByPrimaryKeySelective(suggestionDO);

		return ResultCode.SUCCESS;
	}

	/**
	 * 根据id删除购物车的商品
	 * 
	 * @param id
	 *            购物车id
	 * @param memberId
	 *            会员id
	 * @return
	 * @author Sunny
	 */
	@Transactional
	@Override
	public int remove(Long id, Long memberId) {
		ShoppingCartDO shoppingCartDO = shoppingCartDOMapper.selectByPrimaryKey(id);

		if (shoppingCartDO == null || shoppingCartDO.getId() == null || shoppingCartDO.getId() <= 0) {
			return ResultCode.RESOURCE_NOT_FOUND;
		}

		if (!shoppingCartDO.getMemberId().equals(memberId)) {
			return ResultCode.ILLEGAL_OPERATION;
		}

		shoppingCartDOMapper.deleteByPrimaryKey(id);

		return ResultCode.SUCCESS;
	}

	/**
	 * 根据购物车id列表查询购物车列表
	 * 
	 * @param ids
	 *            购物车id列表
	 * @return
	 */
	@Override
	public List<ShoppingCartBO> findListByIds(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return null;
		}

		ShoppingCartDOExample example = new ShoppingCartDOExample();
		example.createCriteria().andIdIn(ids);

		return ShoppingCartConverter.convertBOS(shoppingCartDOMapper.selectByExample(example));
	}

	public ShoppingCartDO get(Long id) {
		return shoppingCartDOMapper.selectByPrimaryKey(id);
	}

}
