package com.lawu.eshop.mall.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mall.srv.service.CommentProductService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.product.DelProductCommentNotification;

/**
 * 
 * <p>
 * Description: 删除商品型号时，发送消息删除商品型号下的评价
 * </p>
 * @author Yangqh
 * @date 2017年4月18日 下午12:55:22
 *
 */
@Service("delProductCommentTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_PRODUCT_SRV, tags = MqConstant.TAG_DEL_COMMENT)
public class DelProductCommentTransactionFollowServiceImpl extends AbstractTransactionFollowService<DelProductCommentNotification, Reply> {
   
	@Autowired
	private CommentProductService commentProductService;

    @Override
    public Reply execute(DelProductCommentNotification notification) {
        Long productModelId = notification.getProductModelId();
        commentProductService.delCommentByProductModelId(productModelId);
        return new Reply();
    }

}
