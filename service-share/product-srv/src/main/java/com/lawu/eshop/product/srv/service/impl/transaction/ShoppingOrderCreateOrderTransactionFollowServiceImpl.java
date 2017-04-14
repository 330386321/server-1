package com.lawu.eshop.product.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderCreateOrderNotification;
import com.lawu.eshop.product.srv.service.ProductModelService;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_CREATE_ORDER)
public class ShoppingOrderCreateOrderTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingOrderCreateOrderNotification, Reply> {
	
	@Autowired
	private ProductModelService productModelService;
	
	/**
	 * 减库存
	 */
	@Transactional
    @Override
    public Reply execute(ShoppingOrderCreateOrderNotification shoppingOrderCreateOrderNotification) {
    	Reply rtn = new Reply();
    	
    	// 如果接收的消息为空直接返回
    	if (shoppingOrderCreateOrderNotification == null) {
    		return rtn;
    	}
    	
    	// 减商品库存，以及在商品型号库存表添加记录
    	productModelService.lessInventory(shoppingOrderCreateOrderNotification);
    	
		return rtn;
    }
}
