package com.lawu.eshop.order.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingRefundToBeConfirmedForRefundRemindNotification;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 退款中-待商家处理
 * 退款类型-退款
 * 商家处理超时事务-主模块
 * 发送站内信和推送给商家 
 * 
 * @author Sunny
 * @date 2017/04/18
 */
@Service("shoppingRefundToBeConfirmedForRefundRemindTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.TO_BE_CONFIRMED_FOR_REFUND_REMIND, topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_TO_BE_CONFIRMED_FOR_REFUND_REMIND)
public class ShoppingRefundToBeConfirmedForRefundRemindTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingRefundToBeConfirmedForRefundRemindNotification, Reply> {
	
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	/**
	 * 组装其他模块发送的数组
	 */
    @Override
    public ShoppingRefundToBeConfirmedForRefundRemindNotification selectNotification(Long shoppingOrderId) {
    	ShoppingRefundToBeConfirmedForRefundRemindNotification rtn = new ShoppingRefundToBeConfirmedForRefundRemindNotification();
    	
    	ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(shoppingOrderId);
    	rtn.setShoppingOrderId(shoppingOrderBO.getId());
    	rtn.setMerchantNum(shoppingOrderBO.getMerchantNum());
    	
        return rtn;
    }
}
