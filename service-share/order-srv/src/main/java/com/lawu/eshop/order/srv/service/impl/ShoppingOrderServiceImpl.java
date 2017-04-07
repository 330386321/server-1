package com.lawu.eshop.order.srv.service.impl;

import com.lawu.eshop.mall.param.ShoppingOrderSettlementItemParam;
import com.lawu.eshop.mall.param.ShoppingOrderSettlementParam;
import com.lawu.eshop.order.srv.bo.CommentOrderBO;
import com.lawu.eshop.order.srv.converter.ShoppingOrderConverter;
import com.lawu.eshop.order.srv.converter.ShoppingOrderItemConverter;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.domain.ShoppingOrderItemDO;
import com.lawu.eshop.order.srv.mapper.ShoppingCartDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderItemDOMapper;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingOrderServiceImpl implements ShoppingOrderService {
	
	@Autowired
	private ShoppingCartDOMapper shoppingCartDOMapper;
	
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

}
