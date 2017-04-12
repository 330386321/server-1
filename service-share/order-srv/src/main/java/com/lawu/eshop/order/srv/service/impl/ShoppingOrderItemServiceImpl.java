package com.lawu.eshop.order.srv.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.mall.param.foreign.ShoppingRefundQueryForeignParam;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemRefundBO;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemRefundConverter;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample.Criteria;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderItemRefundDO;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderExtendDOMapper;
import com.lawu.eshop.order.srv.service.ShoppingOrderItemService;

@Service
public class ShoppingOrderItemServiceImpl implements ShoppingOrderItemService {
	
	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;
	
	@Autowired
	private ShoppingOrderExtendDOMapper shoppingOrderDOExtendMapper;
	
	/**
	 * 根据购物订单id获取购物订单项
	 * 
	 * @param id 购物订单项id
	 * @return
	 */
	@Override
	public ShoppingOrderItemBO get(Long id) {
		if (id == null || id <= 0) {
			return null;
		}
		
		ShoppingOrderItemDO shoppingOrderItemDO = shoppingOrderItemDOMapper.selectByPrimaryKey(id);
		
		return ShoppingOrderItemConverter.convert(shoppingOrderItemDO);
	}


	/**
	 * 查询处于退款中的订单项
	 * 
	 * @param memberId
	 *            会员id
	 * @param param
	 *            查询参数
	 * @return 订单列表
	 */
	@Override
	public Page<ShoppingOrderItemRefundBO> selectRefundPageByMemberId(Long memberId, ShoppingRefundQueryForeignParam param) {
		
		// 查找所有处于退款中的订单项
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		Criteria criteria = shoppingOrderExtendDOExample.createCriteria();
		criteria.andItemOrderStatusEqualTo(ShoppingOrderStatusEnum.REFUNDING.getValue());
		if (memberId != null && memberId > 0) {
			criteria.andMemberIdEqualTo(memberId);
		}
		
		// 查询总记录数
		Long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);
		
		Page<ShoppingOrderItemRefundBO> shoppingOrderItemRefundBOPage = new Page<ShoppingOrderItemRefundBO>();
		shoppingOrderItemRefundBOPage.setCurrentPage(param.getCurrentPage());
		shoppingOrderItemRefundBOPage.setTotalCount(count.intValue());
		
		if (count == null || count <= 0) {
			return shoppingOrderItemRefundBOPage;
		}
		
		// 按照退款详情的创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("srd.gmt_create desc");
		
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		
		List<ShoppingOrderItemRefundDO> shoppingOrderItemRefundDOList = shoppingOrderDOExtendMapper.selectShoppingOrderItemRefundResultMapByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		shoppingOrderItemRefundBOPage.setRecords(ShoppingOrderItemRefundConverter.convertShoppingOrderItemRefundBOList(shoppingOrderItemRefundDOList));
		
		return shoppingOrderItemRefundBOPage;
	}
	
}