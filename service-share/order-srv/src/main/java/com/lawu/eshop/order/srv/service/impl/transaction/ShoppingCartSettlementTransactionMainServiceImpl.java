package com.lawu.eshop.order.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.mq.constants.MqConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.order.srv.bo.ShoppingCartSettlementNotification;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import com.lawu.eshop.order.srv.domain.ShoppingCartDO;
import com.lawu.eshop.order.srv.mapper.ShoppingCartDOMapper;

/**
 * 
 * @author Sunny
 * @date 2017/04/06
 */
@Service("shoppingCartSettlementTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.SETTLEMENT, topic = MqConstant.TOPIC_ORDER_SRV, tags = "product")
public class ShoppingCartSettlementTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingCartSettlementNotification, Reply> {
	
	@Autowired
	private ShoppingCartDOMapper shoppingCartDOMapper;
	
    @Override
    public ShoppingCartSettlementNotification selectNotification(Long shoppingOrderId) {
        ShoppingCartSettlementNotification regNotification = new ShoppingCartSettlementNotification();
        ShoppingCartDO shoppingCartDO = shoppingCartDOMapper.selectByPrimaryKey(shoppingOrderId);
        regNotification.setProductModelId(shoppingCartDO.getProductModelId());
        regNotification.setQuantity(shoppingCartDO.getQuantity());
        return regNotification;
    }

}
