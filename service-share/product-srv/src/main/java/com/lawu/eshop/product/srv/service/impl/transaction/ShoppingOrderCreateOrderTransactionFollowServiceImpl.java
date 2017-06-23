package com.lawu.eshop.product.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderCreateOrderNotification;
import com.lawu.eshop.mq.dto.order.reply.ShoppingOrderCreateOrderReply;
import com.lawu.eshop.product.srv.service.ProductModelService;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_CREATE_ORDER)
public class ShoppingOrderCreateOrderTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingOrderCreateOrderNotification, ShoppingOrderCreateOrderReply> {
	
	@Autowired
	private ProductModelService productModelService;
	
	/**
	 * 减库存
	 */
	@Transactional
    @Override
    public ShoppingOrderCreateOrderReply execute(ShoppingOrderCreateOrderNotification shoppingOrderCreateOrderNotification) {
		ShoppingOrderCreateOrderReply rtn = null;
    	
    	// 如果接收的消息为空直接返回
    	if (shoppingOrderCreateOrderNotification == null) {
    		return rtn;
    	}
    	
    	/*
    	 *  减商品型号库存
    	 *  减商品总库存
    	 *  以及在商品型号库存表添加记录
    	 *  所有减库存的操作必须在同一个事务中(只要其中一个产品的型号库存不够，全部回滚)
    	 */
    	int resultCode = productModelService.lessInventory(shoppingOrderCreateOrderNotification);
    	
    	rtn = new ShoppingOrderCreateOrderReply();
		rtn.setResultCode(resultCode);
		return rtn;
    }
	
	@Override
	public ShoppingOrderCreateOrderReply getReply(ShoppingOrderCreateOrderNotification notification) {
		ShoppingOrderCreateOrderReply rtn = null;
		
    	// 如果接收的消息为空直接返回
    	if (notification == null) {
    		return rtn;
    	}
		
		int resultCode =  productModelService.checkLessInventory(notification);
		rtn = new ShoppingOrderCreateOrderReply();
		rtn.setResultCode(resultCode);
		return rtn;
	}
}
