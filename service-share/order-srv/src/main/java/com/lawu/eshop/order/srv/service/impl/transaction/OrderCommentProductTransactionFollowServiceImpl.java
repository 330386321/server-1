package com.lawu.eshop.order.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.mall.CommentProductNotification;
import com.lawu.eshop.order.srv.service.ShoppingOrderItemService;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
@Service("orderCommentProductTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_MALL_SRV, tags = MqConstant.TAG_COMMENT_PRODUCT)
public class OrderCommentProductTransactionFollowServiceImpl extends AbstractTransactionFollowService<CommentProductNotification, Reply> {

    @Autowired
    private ShoppingOrderItemService shoppingOrderItemService;
    
    @Override
    public Reply execute(CommentProductNotification notification) {
    	Reply rtn = null;
    	if (notification == null) {
    		return rtn;
    	}
    	
     	Integer result = shoppingOrderItemService.commentsSuccessful(notification.getShoppingOrderItemId());
        if (result == null || result <= 0) {
        	return rtn;
        }
        rtn = new Reply();
        return rtn;
    }
}
