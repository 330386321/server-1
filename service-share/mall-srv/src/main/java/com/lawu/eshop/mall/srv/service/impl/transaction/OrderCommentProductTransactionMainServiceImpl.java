package com.lawu.eshop.mall.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mall.srv.bo.CommentProductNotification;
import com.lawu.eshop.mall.srv.constants.TransactionConstant;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
//@Service("orderCommentTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.COMMENT_PRODUCT, topic = "transaction-comtent_product", tags = "property")
public class OrderCommentProductTransactionMainServiceImpl extends AbstractTransactionMainService<CommentProductNotification> {

    @Override
    public CommentProductNotification selectNotification(Long orderId) {
        CommentProductNotification regNotification = new CommentProductNotification();
        regNotification.setOrderId(orderId);
        return regNotification;
    }
}
