package com.lawu.eshop.product.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.product.srv.bo.transaction.ShoppingCartCreateOrderNotification;
import com.lawu.eshop.product.srv.service.ProductModelService;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_CREATEORDER)
public class ShoppingOrderCreateOrderTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingCartCreateOrderNotification, Reply> {
	
	@Autowired
	private ProductModelService productModelService;
	
	/**
	 * 减库存
	 */
	@Transactional
    @Override
    public Reply execute(ShoppingCartCreateOrderNotification shoppingCartCreateOrderNotification) {
    	Reply rtn = new Reply();
    	
    	// 如果接收的消息为空直接返回
    	if (shoppingCartCreateOrderNotification == null) {
    		return rtn;
    	}
    	
    	// 更新商品库存，以及在商品型号库存表添加记录
    	productModelService.updateInventory(shoppingCartCreateOrderNotification);
    	
		return rtn;
    }
}
