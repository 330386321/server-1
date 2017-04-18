package com.lawu.eshop.order.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingRefundToBeRefundRemindNotification;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 退款中-等待商家退款
 * 商家处理超时
 * 发送提醒事务-主模块
 * 发送站内信和推送给商家 
 * 
 * @author Sunny
 * @date 2017/04/18
 */
@Service("shoppingRefundToBeRefundRemindTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.TO_BE_RETURN_REFUND, topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_TO_BE_RETURN_REMIND)
public class ShoppingRefundToBeRefundRemindTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingRefundToBeRefundRemindNotification, Reply> {
	
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	/**
	 * 组装其他模块发送的数组
	 */
    @Override
    public ShoppingRefundToBeRefundRemindNotification selectNotification(Long shoppingOrderId) {
    	ShoppingRefundToBeRefundRemindNotification rtn = new ShoppingRefundToBeRefundRemindNotification();
    	
    	ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(shoppingOrderId);
    	rtn.setShoppingOrderId(shoppingOrderBO.getId());
    	rtn.setMerchantNum(shoppingOrderBO.getMerchantNum());
    	
        return rtn;
    }
}
