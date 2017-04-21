package com.lawu.eshop.order.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.mall.CommentProductNotification;
import com.lawu.eshop.order.srv.service.ShoppingOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
@Service("orderCommentProductTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_MALL_SRV, tags = MqConstant.TAG_COMMENT_PRODUCT)
public class OrderCommentProductTransactionFollowServiceImpl extends AbstractTransactionFollowService<CommentProductNotification, Reply> {

    @Autowired
    private ShoppingOrderItemService ShoppingOrderItemService;
    
    @Override
    public Reply execute(CommentProductNotification notification) {
        ShoppingOrderItemService.commentsSuccessful(notification.getShoppingOrderItemId());
        return new Reply();
    }
}
