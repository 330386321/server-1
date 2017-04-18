package com.lawu.eshop.mall.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mall.srv.service.MessageService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingRefundToBeConfirmedForReturnRefundRemindNotification;

/**
 * 退款中-待商家处理
 * 退款类型-退货退款
 * 商家处理超时事务-从模块
 * 发送站内信和推送给商家 
 * 
 * @author Sunny
 * @date 2017/04/18
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_TO_BE_CONFIRMED_FOR_RETURN_REFUND_REMIND)
public class ShoppingRefundToBeConfirmedForReturnRefundRemindTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingRefundToBeConfirmedForReturnRefundRemindNotification, Reply> {

	@Autowired
	private MessageService messageService;
	
    @Override
    public Reply execute(ShoppingRefundToBeConfirmedForReturnRefundRemindNotification notification) {
		Reply rtn = null;
		
		if (notification == null) {
			return rtn;
		}
		
		rtn = new Reply();
		
		// 发送站内信
		//messageService.saveMessage(userNum, messageInfoParam);
		
		return rtn;
    }
}
