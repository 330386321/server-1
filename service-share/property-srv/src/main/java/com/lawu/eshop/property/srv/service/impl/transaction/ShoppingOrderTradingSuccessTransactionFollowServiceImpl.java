package com.lawu.eshop.property.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderTradingSuccessNotification;
import com.lawu.eshop.property.param.OrderComfirmDataParam;
import com.lawu.eshop.property.param.OrderSysJobParam;
import com.lawu.eshop.property.srv.service.OrderService;

/**
 * 
 * @author Sunny
 * @date 2017年4月14日
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_TRADING_SUCCESS)
public class ShoppingOrderTradingSuccessTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingOrderTradingSuccessNotification, Reply> {
	    
	@Autowired
	private OrderService orderService;
	
	@Transactional
    @Override
    public Reply execute(ShoppingOrderTradingSuccessNotification notification) {
	    Reply rtn = null;
	    
	    if (notification == null) {
	    	return rtn;
	    }
	    
	    if (!notification.getIsAutoReceipt()) {
		    // 组装请求参数
		    OrderComfirmDataParam param = new OrderComfirmDataParam();
		    param.setBizId(notification.getShoppingOrderId().toString());
		    param.setTotalOrderMoney(notification.getOrderTotalPrice());
		    param.setUserNum(notification.getMerchantNum());
		    orderService.comfirmDelivery(param);
	    } else {
	    	OrderSysJobParam param = new OrderSysJobParam();
	    	param.setOrderActualMoney(notification.getOrderTotalPrice());
	    	param.setOrderIds(notification.getShoppingOrderId().toString());
	    	param.setUserNums(notification.getMerchantNum());
	    	param.setPayWays(new Byte[]{notification.getPaymentMethod().getVal()});
	    	orderService.comfirmSysJob(param);
	    }
	    
	    rtn = new Reply();
        return rtn;
    }
}
