package com.lawu.eshop.order.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderRemindShipmentsNotification;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 提醒卖家发货事务-主模块
 * 
 * @author Sunny
 * @date 2017/04/18
 */
@Service("shoppingOrderAutoRemindShipmentsTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.REMIND_SHIPMENTS, topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_REMIND_SHIPMENTS)
public class ShoppingOrderRemindShipmentsTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingOrderRemindShipmentsNotification, Reply> {
	
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	/**
	 * 组装其他模块发送的数组
	 */
    @Override
    public ShoppingOrderRemindShipmentsNotification selectNotification(Long shoppingOrderId) {
    	ShoppingOrderRemindShipmentsNotification rtn = new ShoppingOrderRemindShipmentsNotification();
    	
    	ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(shoppingOrderId);
    	rtn.setShoppingOrderId(shoppingOrderBO.getId());
    	
        return rtn;
    }
}
