package com.lawu.eshop.mall.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.product.DelProductCommentNotification;

import org.springframework.stereotype.Service;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月18日 下午12:55:22
 *
 */
@Service("delProductCommentTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_PRODUCT_SRV, tags = MqConstant.TAG_DEL_COMMENT)
public class DelProductCommentTransactionFollowServiceImpl extends AbstractTransactionFollowService<DelProductCommentNotification, Reply> {
   

    @Override
    public Reply execute(DelProductCommentNotification notification) {
        Long orderId = notification.getProductModelId();
       
        return new Reply();
    }

}
