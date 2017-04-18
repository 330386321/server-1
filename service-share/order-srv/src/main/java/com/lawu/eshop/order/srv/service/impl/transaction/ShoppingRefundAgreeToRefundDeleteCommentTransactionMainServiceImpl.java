package com.lawu.eshop.order.srv.service.impl.transaction;

import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingRefundAgreeToRefundDeleteCommentNotification;
import com.lawu.eshop.order.srv.constants.TransactionConstant;

/**
 * 商家确认退款事务 - 主模块
 * 发送消息给mall模块删除商品评论
 * 
 * @author Sunny
 * @date 2017/04/15
 */
@Service("shoppingOrderAgreeToRefundDeleteCommentTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.AGREE_TO_REFUND, topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_DELETE_SHOPPING_ORDER)
public class ShoppingRefundAgreeToRefundDeleteCommentTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingRefundAgreeToRefundDeleteCommentNotification, Reply> {
	
	/**
	 * 组装其他模块发送的数组
	 */
    @Override
    public ShoppingRefundAgreeToRefundDeleteCommentNotification selectNotification(Long shoppingOrderItemId) {
    	ShoppingRefundAgreeToRefundDeleteCommentNotification rtn = new ShoppingRefundAgreeToRefundDeleteCommentNotification();
    	rtn.setShoppingOrderItemId(shoppingOrderItemId);
        return rtn;
    }
}
