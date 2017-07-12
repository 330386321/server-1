package com.lawu.eshop.ad.srv.mock.service.mq.message.impl;

import org.springframework.stereotype.Service;

import com.lawu.eshop.mq.message.MessageProducerService;

/**
 * @author Leach
 * @date 2017/7/12
 */
@Service
public class MockRocketMQMessageProducerServiceImpl implements MessageProducerService {
    @Override
    public void sendMessage(String topic, String tags, Object message) {

    }
}
