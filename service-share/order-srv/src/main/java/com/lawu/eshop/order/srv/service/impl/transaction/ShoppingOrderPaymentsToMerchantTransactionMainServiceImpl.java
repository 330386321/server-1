package com.lawu.eshop.order.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderPaymentsToMerchantNotification;
import com.lawu.eshop.mq.dto.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.srv.bo.ShoppingOrderBO;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 订单超过退款时间，打款给商家-主模块
 * 
 * @author Sunny
 * @date 2017/04/14
 */
@Service("shoppingOrderPaymentsToMerchantTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.TRADING_SUCCESS_PAYMENTS_TO_MERCHANT, topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_TRADING_SUCCESS_PAYMENTS_TO_MERCHANT)
public class ShoppingOrderPaymentsToMerchantTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingOrderPaymentsToMerchantNotification, Reply> {
	
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	/**
	 * 组装其他模块发送的数组
	 */
    @Override
    public ShoppingOrderPaymentsToMerchantNotification selectNotification(Long shoppingOrderId) {
    	ShoppingOrderPaymentsToMerchantNotification rtn = new ShoppingOrderPaymentsToMerchantNotification();
    	
    	ShoppingOrderBO shoppingOrderBO = shoppingOrderService.getShoppingOrder(shoppingOrderId);
    	
    	if (shoppingOrderBO == null || shoppingOrderBO.getId() <= 0) {
    		return rtn;
    	}
    	
    	rtn.setMerchantNum(shoppingOrderBO.getMerchantNum());
    	rtn.setMerchantStoreRegionPath(shoppingOrderBO.getMerchantStoreRegionPath());
    	rtn.setShoppingOrderId(shoppingOrderId);
    	rtn.setPaymentMethod(TransactionPayTypeEnum.getEnum(shoppingOrderBO.getPaymentMethod().getVal()));
    	
        return rtn;
    }
}
