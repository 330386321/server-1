package com.lawu.eshop.order.srv.mock.service;

import org.junit.Assert;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.lawu.eshop.mq.message.MessageProducerService;

/**
 * 
 * @author jiangxinjun
 * @date 2017年7月12日
 */
@Primary
@Service
public class MockMessageProducerServiceImpl implements MessageProducerService {

	@Override
	public void sendMessage(String topic, String tags, Object message) {
	    Assert.assertNotNull(topic);
	    Assert.assertNotNull(tags);
	    Assert.assertNotNull(message);
	}

}
