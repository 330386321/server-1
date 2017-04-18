package com.lawu.eshop.order.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingRefundFillReturnAddressRemindNotification;
import com.lawu.eshop.mq.dto.order.ShoppingRefundToBeReturnRemindNotification;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 退款中-等待买家退货
 * 买家处理超时
 * 发送提醒事务-主模块
 * 发送站内信和推送给买家 
 * 
 * @author Sunny
 * @date 2017/04/18
 */
@Service("shoppingRefundToBeReturnRemindTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.TO_BE_RETURN_REMIND, topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_TO_BE_RETURN_REMIND)
public class ShoppingRefundToBeReturnRemindTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingRefundToBeReturnRemindNotification, Reply> {
	
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	/**
	 * 组装其他模块发送的数组
	 */
    @Override
    public ShoppingRefundToBeReturnRemindNotification selectNotification(Long shoppingOrderId) {
    	ShoppingRefundToBeReturnRemindNotification rtn = new ShoppingRefundToBeReturnRemindNotification();
    	
    	ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(shoppingOrderId);
    	rtn.setShoppingOrderId(shoppingOrderBO.getId());
    	rtn.setMerchantNum(shoppingOrderBO.getMerchantNum());
    	
        return rtn;
    }
}
