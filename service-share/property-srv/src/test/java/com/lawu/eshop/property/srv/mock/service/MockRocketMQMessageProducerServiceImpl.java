package com.lawu.eshop.property.srv.mock.service;

import com.lawu.eshop.mq.message.MessageProducerService;
import org.springframework.stereotype.Service;

/**
 * @author yangqh
 * @date 2017/7/13
 */
@Service
public class MockRocketMQMessageProducerServiceImpl implements MessageProducerService {
    @Override
    public void sendMessage(String topic, String tags, Object message) {

    }
}
