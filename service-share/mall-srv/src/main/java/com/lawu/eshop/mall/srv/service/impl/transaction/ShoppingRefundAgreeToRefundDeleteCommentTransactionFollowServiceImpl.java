package com.lawu.eshop.mall.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mall.srv.service.CommentProductService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingRefundAgreeToRefundDeleteCommentNotification;

/**
 * 商家同意退款事务-从模块
 * 删除商品订单评论
 * 
 * @author Sunny
 * @date 2017/04/18
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_REMIND_SHIPMENTS)
public class ShoppingRefundAgreeToRefundDeleteCommentTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingRefundAgreeToRefundDeleteCommentNotification, Reply> {

	@Autowired
	private CommentProductService commentProductService;
	
    @Override
    public Reply execute(ShoppingRefundAgreeToRefundDeleteCommentNotification notification) {
		Reply rtn = null;
		
		if (notification == null) {
			return rtn;
		}
		
		rtn = new Reply();
		
		// 发送站内信
		commentProductService.delCommentByOrderItemId(notification.getShoppingOrderItemId());
		
		return rtn;
    }
}
