package com.lawu.eshop.order.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingRefundRefundFailedRemindNotification;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 退款中-商家拒绝退款
 * 买家处理超时
 * 发送提醒事务-主模块
 * 发送站内信和推送给买家 
 * 
 * @author Sunny
 * @date 2017/04/18
 */
@Service("shoppingRefundRefundFailedRemindTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.REFUND_FAILED_REMIND, topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_REFUND_FAILED_REMIND)
public class ShoppingRefundRefundFailedRemindTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingRefundRefundFailedRemindNotification, Reply> {
	
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	/**
	 * 组装其他模块发送的数组
	 */
    @Override
    public ShoppingRefundRefundFailedRemindNotification selectNotification(Long shoppingOrderId) {
    	ShoppingRefundRefundFailedRemindNotification rtn = new ShoppingRefundRefundFailedRemindNotification();
    	
    	ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(shoppingOrderId);
    	rtn.setShoppingOrderId(shoppingOrderBO.getId());
    	rtn.setMerchantNum(shoppingOrderBO.getMerchantNum());
    	
        return rtn;
    }
}
