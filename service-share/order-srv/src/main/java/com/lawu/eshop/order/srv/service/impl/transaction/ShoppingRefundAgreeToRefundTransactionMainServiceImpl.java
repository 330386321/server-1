package com.lawu.eshop.order.srv.service.impl.transaction;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingRefundAgreeToRefundNotification;
import com.lawu.eshop.mq.dto.order.constants.OrderRefundStatusEnum;
import com.lawu.eshop.mq.dto.order.constants.TransactionPayTypeEnum;
import com.lawu.eshop.order.constants.ShoppingOrderStatusEnum;
import com.lawu.eshop.order.srv.bo.ShoppingOrderExtendBO;
import com.lawu.eshop.order.srv.bo.ShoppingOrderItemBO;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;
import com.lawu.eshop.utils.NumberUtil;

/**
 * 商家确认退款事务-主模块
 * 发送消息给property模块，退款给买家
 * 
 * @author Sunny
 * @date 2017/04/15
 */
@Service("shoppingRefundAgreeToRefundTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.AGREE_TO_REFUND, topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_AGREE_TO_REFUND)
public class ShoppingRefundAgreeToRefundTransactionMainServiceImpl extends AbstractTransactionMainService<ShoppingRefundAgreeToRefundNotification, Reply> {
	
	@Autowired
	private ShoppingOrderService shoppingOrderService;
	
	/**
	 * 组装其他模块发送的数组
	 */
    @Override
    public ShoppingRefundAgreeToRefundNotification selectNotification(Long shoppingOrderItemId) {
    	ShoppingRefundAgreeToRefundNotification rtn = new ShoppingRefundAgreeToRefundNotification();
    	
    	ShoppingOrderExtendBO shoppingOrderExtendBO = shoppingOrderService.getByShoppingOrderItemId(shoppingOrderItemId);
    	
    	if (shoppingOrderExtendBO == null || shoppingOrderExtendBO.getId() == null || shoppingOrderExtendBO.getId() <= 0) {
    		return rtn;
    	}
    	
    	boolean isLast = true;
    	BigDecimal refundMoney = null;
    	OrderRefundStatusEnum status = null;
    	for (ShoppingOrderItemBO shoppingOrderItemBO : shoppingOrderExtendBO.getItems()) {
    		if (!shoppingOrderItemBO.getOrderStatus().equals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION) && !shoppingOrderItemBO.getId().equals(shoppingOrderItemId)) {
    			isLast = false;
    		}
    		
    		if (shoppingOrderItemBO.getId().equals(shoppingOrderItemId)) {
    			refundMoney = shoppingOrderItemBO.getSalesPrice().multiply(new BigDecimal(shoppingOrderItemBO.getQuantity()));
    			
    			if (shoppingOrderItemBO.getOrderStatus().equals(ShoppingOrderStatusEnum.CANCEL_TRANSACTION)
        				|| shoppingOrderItemBO.getOrderStatus().equals(ShoppingOrderStatusEnum.TRADING_SUCCESS)) {
    				status = OrderRefundStatusEnum.FINISH;
        		} else {
        			status = OrderRefundStatusEnum.IN_PROCESSING;
        		}
    		}
    	}
    	
    	rtn.setMenberNum(shoppingOrderExtendBO.getMemberNum());
    	rtn.setMerchantNum(shoppingOrderExtendBO.getMerchantNum());
    	rtn.setShoppingOrderId(shoppingOrderExtendBO.getId());
    	rtn.setShoppingOrderItemId(shoppingOrderItemId);
    	rtn.setPaymentMethod(TransactionPayTypeEnum.getEnum(shoppingOrderExtendBO.getPaymentMethod().getVal()));
    	rtn.setRefundMoney(NumberUtil.format(refundMoney));
    	rtn.setIsLast(isLast);
    	rtn.setThirdNumber(shoppingOrderExtendBO.getThirdNumber());
    	rtn.setStatus(status);
    	
        return rtn;
    }
}
