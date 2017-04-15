package com.lawu.eshop.order.srv.service.impl.transaction;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderAutoCommentNotification;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemEvaluationBO;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 商品订单自动好评事务-主模块
 * 
 * @author Sunny
 * @date 2017/04/14
 */
@Service("shoppingOrderAutoCommentTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.AUTO_COMMENT, topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_AUTO_COMMENT)
public class ShoppingOrderAutoCommentTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingOrderAutoCommentNotification, Reply> {
	
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	
	/**
	 * 组装其他模块发送的数组
	 */
    @Override
    public ShoppingOrderAutoCommentNotification selectNotification(Long shoppingOrderId) {
    	ShoppingOrderAutoCommentNotification rtn = new ShoppingOrderAutoCommentNotification();
    	
    	ShoppingOrderItemEvaluationBO shoppingOrderItemEvaluationBO = shoppingOrderService.getShoppingOrderItemEvaluationBOByShoppingOrderItemId(shoppingOrderId);
    	BeanUtils.copyProperties(shoppingOrderItemEvaluationBO, rtn);
    	
        return rtn;
    }
}
