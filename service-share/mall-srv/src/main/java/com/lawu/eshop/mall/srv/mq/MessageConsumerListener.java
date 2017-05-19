package com.lawu.eshop.mall.srv.mq;

import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageTempParam;
import com.lawu.eshop.mall.srv.service.MessageService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.ShoppingOrderNoPaymentNotification;
import com.lawu.eshop.mq.dto.order.ShoppingOrderpaymentSuccessfulNotification;
import com.lawu.eshop.mq.message.impl.AbstractMessageConsumerListener;

/**
 * 
 * @author Sunny
 * @date 2017年4月17日
 */
public class MessageConsumerListener extends AbstractMessageConsumerListener {

	@Autowired
	private MessageService messageService;

	@Override
	public void consumeMessage(String topic, String tags, Object message) {
		
		if (MqConstant.TOPIC_ORDER_SRV.equals(topic)) {
			// 用户付款成功提示买家新增一个订单
			if (MqConstant.TAG_PAYMENT_SUCCESSFUL_PUSH_TO_MERCHANT.equals(tags)) {
				ShoppingOrderpaymentSuccessfulNotification notification = (ShoppingOrderpaymentSuccessfulNotification) message;
				/*
				 * 发送站内信
				 */
				// 组装信息
				MessageInfoParam MessageInfoParam = new MessageInfoParam();
				MessageInfoParam.setRelateId(notification.getId());
				MessageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_NEW_ORDER);
	
				MessageInfoParam.setMessageParam(new MessageTempParam());
				MessageInfoParam.getMessageParam().setOrderNum(notification.getOrderNum());
	
				// 保存站内信，并且发送个推
				messageService.saveMessage(notification.getMerchantNum(), MessageInfoParam);
	
			} else
			// 用户付款成功提示买家新增一个订单
			if (MqConstant.TAG_ORDER_NO_PAYMENT_PUSH_TO_MEMBER.equals(tags)) {
				ShoppingOrderNoPaymentNotification notification = (ShoppingOrderNoPaymentNotification) message;
				/*
				 * 发送站内信
				 */
				// 组装信息
				MessageInfoParam MessageInfoParam = new MessageInfoParam();
				MessageInfoParam.setRelateId(notification.getId());
				MessageInfoParam.setTypeEnum(MessageTypeEnum.MESSAGE_TYPE_ORDER_PAY);
	
				MessageInfoParam.setMessageParam(new MessageTempParam());
				MessageInfoParam.getMessageParam().setOrderNum(notification.getOrderNum());
	
				// 保存站内信，并且发送个推
				messageService.saveMessage(notification.getMemberNum(), MessageInfoParam);
			}
		}
	}
}
