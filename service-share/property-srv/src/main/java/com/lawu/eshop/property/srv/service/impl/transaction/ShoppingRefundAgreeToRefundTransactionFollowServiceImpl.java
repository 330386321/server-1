package com.lawu.eshop.property.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingRefundAgreeToRefundNotification;
import com.lawu.eshop.property.constants.OrderRefundStatusEnum;
import com.lawu.eshop.property.param.OrderRefundDataParam;
import com.lawu.eshop.property.srv.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Sunny
 * @date 2017年4月17日
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_AGREE_TO_REFUND)
public class ShoppingRefundAgreeToRefundTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingRefundAgreeToRefundNotification, Reply> {
	
	private static Logger logger = LoggerFactory.getLogger(ShoppingRefundAgreeToRefundTransactionFollowServiceImpl.class);
	    
	@Autowired
	private OrderService orderService;
	
	@Transactional
    @Override
    public Reply execute(ShoppingRefundAgreeToRefundNotification notification) {
	    Reply rtn = new Reply();
	    
	    if (notification == null) {
	    	return rtn;
	    }
	    
	    
	    // 组装请求参数
	    OrderRefundDataParam param = new OrderRefundDataParam();
	    param.setLast(notification.getIsLast());
	    param.setOrderId(notification.getShoppingOrderId().toString());
	    param.setOrderItemIds(notification.getShoppingOrderItemId().toString());
	    param.setRefundMoney(notification.getRefundMoney());
	    param.setSideUserNum(notification.getMerchantNum());
	    param.setTradeNo(notification.getThirdNumber());
	    param.setOrderRefundStatusEnum(OrderRefundStatusEnum.getEnum(notification.getStatus().getValue()));
	    
	    try {
	    	orderService.doRefundScopeInside(param);
	    } catch (Exception e) {
	    	logger.error(e.getMessage());
	    }
    	
        return rtn;
    }
}
