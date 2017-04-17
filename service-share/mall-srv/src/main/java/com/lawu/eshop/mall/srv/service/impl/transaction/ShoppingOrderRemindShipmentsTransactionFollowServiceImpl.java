package com.lawu.eshop.mall.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mall.srv.service.MessageService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderRemindShipmentsNotification;

/**
 * 商品订单自动好评事务-从模块
 * 
 * @author Sunny
 * @date 2017年4月15日
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_REMIND_SHIPMENTS)
public class ShoppingOrderRemindShipmentsTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingOrderRemindShipmentsNotification, Reply> {

	@Autowired
	private MessageService messageService;
	
    @Override
    public Reply execute(ShoppingOrderRemindShipmentsNotification notification) {
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
