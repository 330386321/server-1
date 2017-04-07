package com.lawu.eshop.order.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.order.srv.bo.CommentProductNotification;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
//@Service
@CompensatingTransactionFollow(topic = "transaction-comtent_product", tags = "order")
public class OrderCommentProductTransactionFollowServiceImpl extends AbstractTransactionFollowService<CommentProductNotification> {

    @Autowired
    private ShoppingOrderDOMapper shoppingOrderDOMapper;
    @Override
    public void execute(CommentProductNotification notification) {
        Long orderId = notification.getOrderId();
        ShoppingOrderDO order = shoppingOrderDOMapper.selectByPrimaryKey(orderId);
        if(order == null){
            return;
        }
        ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
        shoppingOrderDO.setId(orderId);
        shoppingOrderDO.setIsEvaluation(true);//设置评价
        shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);
    }
}
