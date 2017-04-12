package com.lawu.eshop.mall.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mall.srv.bo.CommentMerchantNotification;
import com.lawu.eshop.mall.srv.constants.TransactionConstant;
import com.lawu.eshop.mq.constants.MqConstant;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong
 * @date 2017/4/12.
 */
@Service("orderCommentMerchantTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.COMMENT_MERCHANT, topic = MqConstant.TOPIC_MALL_SRV, tags = MqConstant.TAG_COMMENT_MERCHANT)
public class OrderCommentMerchantTransactionMainServiceImpl extends AbstractTransactionMainService<CommentMerchantNotification, Reply> {
    @Override
    public CommentMerchantNotification selectNotification(Long payOrderId) {
        CommentMerchantNotification notification = new CommentMerchantNotification();
        notification.setPayOrderId(payOrderId);
        return notification;
    }
}
