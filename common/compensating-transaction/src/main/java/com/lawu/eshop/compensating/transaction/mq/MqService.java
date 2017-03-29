package com.lawu.eshop.compensating.transaction.mq;

/**
 * @author Leach
 * @date 2017/3/29
 */
public interface MqService<T> {

    /**
     * 发送MQ消息
     *
     * @param topic
     * @param tags
     * @param body
     */
    void sendMessage(String topic, String tags, T body);
}
