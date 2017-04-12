package com.lawu.eshop.order.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.order.srv.bo.CommentProductNotification;
import com.lawu.eshop.order.srv.domain.ShoppingOrderDO;
import com.lawu.eshop.order.srv.mapper.ShoppingOrderDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_MALL_SRV, tags = MqConstant.TAG_COMMENT_PRODUCT)
public class OrderCommentProductTransactionFollowServiceImpl extends AbstractTransactionFollowService<CommentProductNotification, Reply> {

    @Autowired
    private ShoppingOrderDOMapper shoppingOrderDOMapper;
    @Override
    public Reply execute(CommentProductNotification notification) {
        Long orderId = notification.getOrderId();
        ShoppingOrderDO order = shoppingOrderDOMapper.selectByPrimaryKey(orderId);
        if(order == null){
            return new Reply();
        }
        ShoppingOrderDO shoppingOrderDO = new ShoppingOrderDO();
        shoppingOrderDO.setId(orderId);
        shoppingOrderDO.setIsEvaluation(true);//设置评价
        shoppingOrderDOMapper.updateByPrimaryKeySelective(shoppingOrderDO);

        return new Reply();
    }
}
