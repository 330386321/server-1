package com.lawu.eshop.user.srv.mock.service;

import com.lawu.eshop.mq.message.MessageProducerService;
import org.springframework.stereotype.Service;

/**
 * @author meishuquan
 * @date 2017/7/13.
 */
@Service
public class MockMessageProducerService implements MessageProducerService {

    @Override
    public void sendMessage(String topic, String tags, Object message) {

    }
}
