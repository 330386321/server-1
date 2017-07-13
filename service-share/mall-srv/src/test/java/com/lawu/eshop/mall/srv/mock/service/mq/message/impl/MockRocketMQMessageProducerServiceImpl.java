package com.lawu.eshop.mall.srv.mock.service.mq.message.impl;

import com.lawu.eshop.mq.message.MessageProducerService;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong
 * @date 2017/7/13
 */
@Service
public class MockRocketMQMessageProducerServiceImpl implements MessageProducerService {
    @Override
    public void sendMessage(String topic, String tags, Object message) {

    }
}
