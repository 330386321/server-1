/**
 * 
 */
package com.lawu.eshop.product.srv.mock.service.mq.message.impl;

import org.springframework.stereotype.Service;

import com.lawu.eshop.mq.message.MessageProducerService;

/**
 * @author lihj
 * @date 2017年7月24日
 */
@Service
public class MockRocketMQMessageProducerServiceImpl implements MessageProducerService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lawu.eshop.mq.message.MessageProducerService#sendMessage(java.lang.
	 * String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void sendMessage(String topic, String tags, Object message) {

	}

}
