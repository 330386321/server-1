package com.lawu.eshop.mq.message.impl;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.lawu.eshop.mq.consumer.CustomConsumer;
import com.lawu.eshop.mq.consumer.CustomConsumerRegister;
import com.lawu.eshop.mq.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * 设备报文消费监听
 * @author Leach
 * @date 2017/4/9
 */
public abstract class AbstractMessageConsumerListener implements MessageListenerConcurrently {

    private Logger logger = LoggerFactory.getLogger(AbstractMessageConsumerListener.class);

    @Autowired
    private CustomConsumerRegister customConsumerRegister;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt messageExt : msgs) {
            String topic = messageExt.getTopic();
            String tags = messageExt.getTags();
            byte[] body = messageExt.getBody();
            Object object;
            try {
                object = ByteUtil.byteToObject(body);
            } catch (IOException | ClassNotFoundException e) {
                logger.error("Failure consumption, later try to consume", e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            if (customConsumerRegister != null) {
                CustomConsumer customConsumer = customConsumerRegister.getConsumer(topic, tags);

                // 自定义优先
                if (customConsumer != null) {
                    customConsumer.consumeMessage(object);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            }

            consumeMessage(topic, tags, object);

        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    public abstract void consumeMessage(String topic, String tags, Object message);
}
