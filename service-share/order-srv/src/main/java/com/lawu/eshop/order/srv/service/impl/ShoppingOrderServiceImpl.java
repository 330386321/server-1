package com.lawu.eshop.order.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementItemParam;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.mall.param.foreign.ShoppingOrderQueryForeignParam;
import com.lawu.eshop.order.srv.bo.CommentOrderBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendQueryBO;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderExtendConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemConverter;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDO;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample;
import com.lawu.eshop.order.srv.domain.extend.ShoppingOrderExtendDOExample.Criteria;
import com.lawu.eshop.order.srv.mapper.ShoppingCartDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.mapper.extend.ShoppingOrderExtendDOMapper;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderService {
	
	@Autowired
	private ShoppingCartDOMapper shoppingCartDOMapper;
	
	@Autowired
	private ShoppingOrderExtendDOMapper shoppingOrderDOExtendMapper;
	
	@Autowired
	private ShoppingOrderDOMapper shoppingOrderDOMapper;
	
	@Autowired
	private ShoppingOrderItemDOMapper shoppingOrderItemDOMapper;
	
    //@Autowired
    //private TransactionMainService transactionMainService;
	
	/**
	 * 
	 * @param params 多个订单参数
	 * @return 返回保存的订单id
	 */
	@Transactional
	@Override
	public List<Long> save(List<ShoppingOrderSettlementParam> params) {
		
		List<Long> rtn = new ArrayList<Long>();
		
		// 插入订单
		for (ShoppingOrderSettlementParam shoppingOrderSettlementParam : params) {
			ShoppingOrderDO shoppingOrderDO =  ShoppingOrderConverter.convert(shoppingOrderSettlementParam);
			shoppingOrderDOMapper.insertSelective(shoppingOrderDO);
			Long id = shoppingOrderDO.getId();
			
			// 把订单id放入list返回
			rtn.add(id);
			
			//插入订单项
			for (ShoppingOrderSettlementItemParam item : shoppingOrderSettlementParam.getItems()) {
				ShoppingOrderItemDO shoppingOrderItemDO = ShoppingOrderItemConverter.convert(id, item);
				shoppingOrderItemDOMapper.insertSelective(shoppingOrderItemDO);
				
				//删除购物车记录,保证在同一个事务中进行
				shoppingCartDOMapper.deleteByPrimaryKey(item.getShoppingCartId());
			}
			
			// TODO 事务补偿预留
			//transactionMainService.sendNotice(id);
		}
		
		return rtn;
	}

	@Override
	public CommentOrderBO getOrderCommentStatusById(Long orderId) {
		ShoppingOrderDO shoppingOrderDO = shoppingOrderDOMapper.selectByPrimaryKey(orderId);
		CommentOrderBO commentOrderBO = ShoppingOrderConverter.coverCommentStatusBO(shoppingOrderDO);
		return commentOrderBO;
	}
	
	@Override
	public Page<ShoppingOrderExtendQueryBO> selectPageByMemberId(Long memberId, ShoppingOrderQueryForeignParam param) {
		ShoppingOrderExtendDOExample shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		
		// 组装Criteria
		Criteria baseCriteria = shoppingOrderExtendDOExample.createCriteria();
		
		// 用户如果删除则不显示
		baseCriteria.andStatusEqualTo((byte)0x01);
		
		if (memberId != null) {
			baseCriteria.andMemberIdEqualTo(memberId);
		}
		
		if (param.getIsEvaluation() != null) {
			baseCriteria.andIsEvaluationEqualTo(param.getIsEvaluation());
		}
		
		if (param.getOrderStatus() != null) {
			baseCriteria.andOrderStatusEqualTo(param.getOrderStatus().getValue());
		}
		
		if (!StringUtils.isEmpty(param.getKeyword())) {
			shoppingOrderExtendDOExample.clear();
			
			Criteria orderNumCriteria = shoppingOrderExtendDOExample.or();
			orderNumCriteria.andOrderNumEqualTo(param.getKeyword());
			orderNumCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
			
			Criteria paroductCriteria = shoppingOrderExtendDOExample.or();
			paroductCriteria.andProductNameLike("%"+ param.getKeyword() + "%");
			paroductCriteria.getAllCriteria().addAll(baseCriteria.getAllCriteria());
		}
		
		// 过滤重复记录
		shoppingOrderExtendDOExample.setDistinct(true);
		
		// 查询总记录数
		Long count = shoppingOrderDOExtendMapper.countByExample(shoppingOrderExtendDOExample);
		
		Page<ShoppingOrderExtendQueryBO> shoppingOrderItemBOPage = new Page<ShoppingOrderExtendQueryBO>();
		shoppingOrderItemBOPage.setTotalCount(count.intValue());
		shoppingOrderItemBOPage.setCurrentPage(param.getCurrentPage());
		
		// 如果总记录为0，不再执行后续操作直接返回
		if (count == null || count <= 0) {
			return shoppingOrderItemBOPage;
		}
		
		// 分页参数
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		
		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		
		// 如果参数中的keyword有值，查询结果的订单项会缺少，所有先找出所有购物订单id再通过去查找购物订单以及级联的购物订单项
		List<Long> shoppingOrderIdList = shoppingOrderDOExtendMapper.selectShoppingOrderIdByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		shoppingOrderExtendDOExample = new ShoppingOrderExtendDOExample();
		shoppingOrderExtendDOExample.createCriteria().andIdIn(shoppingOrderIdList);
		
		// 默认创建时间排序
		shoppingOrderExtendDOExample.setOrderByClause("so.gmt_create desc");
		
		List<ShoppingOrderExtendDO> shoppingOrderExtendDOList = shoppingOrderDOExtendMapper.selectShoppingOrderAssociationByExampleWithRowbounds(shoppingOrderExtendDOExample, rowBounds);
		
		shoppingOrderItemBOPage.setRecords(ShoppingOrderExtendConverter.convert(shoppingOrderExtendDOList));
		
		return shoppingOrderItemBOPage;
	}

}
