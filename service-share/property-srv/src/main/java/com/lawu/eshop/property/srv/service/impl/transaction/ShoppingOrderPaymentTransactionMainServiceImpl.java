package com.lawu.eshop.property.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.property.ShoppingOrderPaymentNotification;
import com.lawu.eshop.property.srv.constans.TransactionConstant;
import com.lawu.eshop.property.srv.domain.TransactionDetailDO;
import com.lawu.eshop.property.srv.mapper.TransactionDetailDOMapper;

/**
 * 支付购物订单事务处理-主模块
 * 
 * @author Sunny
 * @date 2017年4月14日
 */
@Service("shoppingOrderPaymentTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.PAY_SHOPPING_ORDER, topic = MqConstant.TOPIC_PROPERTY_SRV, tags = MqConstant.TAG_PAY_SHOPPING_ORDER)
public class ShoppingOrderPaymentTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingOrderPaymentNotification, Reply> {
    
	@Autowired
	private TransactionDetailDOMapper transactionDetailDOMapper;
	
	@Override
    public ShoppingOrderPaymentNotification selectNotification(Long transactionDetailId) {
		
		TransactionDetailDO transactionDetailDO = transactionDetailDOMapper.selectByPrimaryKey(transactionDetailId);
		
    	ShoppingOrderPaymentNotification notification = new ShoppingOrderPaymentNotification();
    	
        notification.setShoppingOrderIds(transactionDetailDO.getBizId());
        notification.setThirdNumber(transactionDetailDO.getThirdTransactionNum());
        notification.setPaymentMethod(transactionDetailDO.getTransactionAccountType());
        
        return notification;
    }
}
