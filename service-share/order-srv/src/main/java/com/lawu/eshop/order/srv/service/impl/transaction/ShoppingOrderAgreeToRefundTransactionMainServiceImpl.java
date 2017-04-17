package com.lawu.eshop.order.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderTradingSuccessNotification;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 商家确认退款-主模块
 * 
 * @author Sunny
 * @date 2017/04/15
 */
@Service("shoppingOrderAgreeToRefundTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.AGREE_TO_REFUND, topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_AGREE_TO_REFUND)
public class ShoppingOrderAgreeToRefundTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingOrderTradingSuccessNotification, Reply> {
	
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	/**
	 * 组装其他模块发送的数组
	 */
    @Override
    public ShoppingOrderTradingSuccessNotification selectNotification(Long shoppingOrderId) {
    	ShoppingOrderTradingSuccessNotification rtn = null;
    	
    	ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(shoppingOrderId);
    	
    	if (shoppingOrderBO == null || shoppingOrderBO.getId() == null || shoppingOrderBO.getId() <= 0) {
    		return rtn;
    	}
    	
    	rtn = new ShoppingOrderTradingSuccessNotification();
    	
    	
        return rtn;
    }
}
