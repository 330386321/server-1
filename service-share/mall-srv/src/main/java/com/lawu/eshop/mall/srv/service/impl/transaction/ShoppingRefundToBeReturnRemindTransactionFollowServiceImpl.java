package com.lawu.eshop.mall.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mall.srv.service.MessageService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingRefundToBeReturnRemindNotification;

/**
 * 退款中-等待买家退货
 * 买家处理超时
 * 发送提醒事务-从模块
 * 发送站内信和推送给买家 
 * 
 * @author Sunny
 * @date 2017/04/18
 */
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_TO_BE_RETURN_REMIND)
public class ShoppingRefundToBeReturnRemindTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingRefundToBeReturnRemindNotification, Reply> {

	@Autowired
	private MessageService messageService;
	
    @Override
    public Reply execute(ShoppingRefundToBeReturnRemindNotification notification) {
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
