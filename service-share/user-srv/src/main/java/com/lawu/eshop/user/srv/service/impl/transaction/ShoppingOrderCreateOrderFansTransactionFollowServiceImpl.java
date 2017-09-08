package com.lawu.eshop.user.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderCreateOrderFansNotification;
import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import com.lawu.eshop.user.srv.bo.FansMerchantBO;
import com.lawu.eshop.user.srv.service.FansMerchantService;

/**
 * 创建购物订单事务
 * 用户成为商家粉丝-主模块
 * 
 * @author Sunny
 * @date 2017年5月3日
 */
@Service("shoppingOrderCreateOrderFansTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = MqConstant.TAG_CREATE_ORDER_FANS)
public class ShoppingOrderCreateOrderFansTransactionFollowServiceImpl extends AbstractTransactionFollowService<ShoppingOrderCreateOrderFansNotification, Reply> {

	@Autowired
	private FansMerchantService fansMerchantService;
	
	/**
	 * 接收订单模块创建订单发送的消息
	 * 用户成为商家的粉丝
	 */
    @Transactional
    @Override
	public void execute(ShoppingOrderCreateOrderFansNotification notification) {
		FansMerchantBO fansMerchantBO = fansMerchantService.getFansMerchant(notification.getMemberId(), notification.getMerchantId());
		if (fansMerchantBO == null) {
			fansMerchantService.saveFansMerchant(notification.getMerchantId(), notification.getMemberId(), FansMerchantChannelEnum.ORDER_PAY);
		}
	}
}