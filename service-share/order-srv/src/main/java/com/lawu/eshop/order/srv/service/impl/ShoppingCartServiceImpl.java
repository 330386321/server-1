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
		
		// 查看用户购物车是否有重复记录
		ShoppingCartDOExample example = new ShoppingCartDOExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andProductModelIdEqualTo(param.getProductModelId());
		List<ShoppingCartDO> list = shoppingCartDOMapper.selectByExample(example);
		
		ShoppingCartDO shoppingCartDO = null;
		if (list != null && !list.isEmpty()) {
			shoppingCartDO = list.get(0);
			shoppingCartDO.setGmtModified(new Date());
			shoppingCartDO.setQuantity(shoppingCartDO.getQuantity() + param.getQuantity());
			shoppingCartDOMapper.updateByPrimaryKeySelective(shoppingCartDO);
			
			return successCreated(shoppingCartDO.getId());
		} else {
			shoppingCartDO = ShoppingCartConverter.convert(param);
			shoppingCartDO.setMemberId(memberId);
			shoppingCartDO.setGmtCreate(new Date());
			shoppingCartDO.setGmtModified(new Date());
	
			// 空值交给Mybatis去处理
			int result = shoppingCartDOMapper.insertSelective(shoppingCartDO);
	
			if (result <= 0) {
				return successCreated(ResultCode.SAVE_FAIL);
			}
		}
		
		return successCreated(shoppingCartDO.getId());
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
	public int remove(Long memberId, List<Long> ids) {
		ShoppingCartDOExample example = new ShoppingCartDOExample();
		example.createCriteria().andIdIn(ids);
		
		shoppingCartDOMapper.deleteByExample(example);

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
	public Result<List<ShoppingCartBO>> findListByIds(List<Long> ids) {
		
		if (ids == null || ids.isEmpty()) {
			return successGet(ResultCode.ID_EMPTY);
		}

		ShoppingCartDOExample example = new ShoppingCartDOExample();
		example.createCriteria().andIdIn(ids);

		List<ShoppingCartDO> list = shoppingCartDOMapper.selectByExample(example);
		if (list == null || list.isEmpty()) {
			return successGet(ResultCode.RESOURCE_NOT_FOUND);
		}
		
		return successGet(ShoppingCartConverter.convertBOS(list));
	}

	/**
	 * 根据用户id列表查询购物车数量
	 * 
	 * @param memberId 用户id
	 * @return
	 */
	@Override
	public Long count(Long memberId) {
		ShoppingCartDOExample example = new ShoppingCartDOExample();
		example.createCriteria().andMemberIdEqualTo(memberId);
		return shoppingCartDOMapper.countByExample(example);
	}

}
