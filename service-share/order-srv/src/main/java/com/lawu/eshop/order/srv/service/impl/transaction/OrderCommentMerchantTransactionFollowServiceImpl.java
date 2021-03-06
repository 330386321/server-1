package com.lawu.eshop.order.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.mall.CommentMerchantNotification;
import com.lawu.eshop.mq.message.MessageProducerService;
import com.lawu.eshop.order.srv.domain.PayOrderDO;
import com.lawu.eshop.order.srv.mapper.PayOrderDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangyong
 * @date 2017/4/12.
 */
@Service("orderCommentMerchantTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_MALL_SRV, tags = MqConstant.TAG_COMMENT_MERCHANT)
public class OrderCommentMerchantTransactionFollowServiceImpl extends AbstractTransactionFollowService<CommentMerchantNotification, Reply> {

	@Autowired
	private PayOrderDOMapper payOrderDOMapper;

	@Autowired
	private MessageProducerService messageProducerService;

	@Override
	public void execute(CommentMerchantNotification notification) {
		PayOrderDO order = new PayOrderDO();
		order.setId(notification.getPayOrderId());
		order.setCommentTime(new Date());
		order.setIsEvaluation(true);
		payOrderDOMapper.updateByPrimaryKeySelective(order);

		PayOrderDO orderDO = payOrderDOMapper.selectByPrimaryKey(notification.getPayOrderId());
		messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV, MqConstant.TAG_COMMENTS_COUNT, orderDO.getMerchantId());
	}
}
