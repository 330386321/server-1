package com.lawu.eshop.order.srv.mq;

import com.lawu.eshop.mq.message.impl.AbstractMessageConsumerListener;

/**
 * 
 * @author Sunny
 * @date 2017年4月13日
 */
public class MessageConsumerListener extends AbstractMessageConsumerListener {
    @Override
    public void consumeMessage(String topic, String tags, Object message) {

    }
}
