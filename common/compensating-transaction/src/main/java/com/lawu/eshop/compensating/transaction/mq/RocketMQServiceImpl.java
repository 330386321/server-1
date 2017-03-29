package com.lawu.eshop.compensating.transaction.mq;

import org.springframework.stereotype.Service;

/**
 * @author Leach
 * @date 2017/3/29
 */
@Service
public class RocketMQServiceImpl implements MqService {

    @Override
    public void sendMessage(String topic, String tags, Object body) {

    }
}
