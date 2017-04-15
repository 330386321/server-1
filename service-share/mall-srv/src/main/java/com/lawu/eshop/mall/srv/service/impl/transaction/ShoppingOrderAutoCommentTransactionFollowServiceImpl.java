package com.lawu.eshop.mall.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mall.srv.service.CommentProductService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderAutoCommentNotification;

/**
 * 商品订单自动好评事务-从模块
 * 
 * @author Sunny
 * @date 2017年4月15日
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_AUTO_COMMENT)
public class ShoppingOrderAutoCommentTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingOrderAutoCommentNotification, Reply> {

	@Autowired
	private CommentProductService commentProductService;
	
	@Transactional
    @Override
    public Reply execute(ShoppingOrderAutoCommentNotification notification) {
		Reply rtn = new Reply();
		
		if (notification == null) {
			return rtn;
		}
		
		commentProductService.saveCommentProductInfoOrderJob(notification.getMemberId(), notification.getProductId(), notification.getShoppingOrderItem());
    	
        return rtn;
    }
}
