package com.lawu.eshop.product.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderTradingSuccessIncreaseSalesNotification;
import com.lawu.eshop.product.srv.service.ProductModelService;

/**
 * 确认收货
 * 添加商品销量-从事务
 * 
 * @author Sunny
 * @date 2017/04/06
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_TRADING_SUCCESS_INCREASE_SALES)
public class ShoppingOrderTradingSuccessIncreaseSalesTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingOrderTradingSuccessIncreaseSalesNotification, Reply> {
	
	@Autowired
	private ProductModelService productModelService;
	
	/**
	 * 添加销量
	 */
	@Transactional
    @Override
    public void execute(ShoppingOrderTradingSuccessIncreaseSalesNotification notification) {
    	/*
    	 * 添加商品型号销量
    	 * 添加商品总销量
    	 */
    	productModelService.increaseSales(notification);
    }
}
