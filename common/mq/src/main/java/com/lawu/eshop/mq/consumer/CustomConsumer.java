package com.lawu.eshop.mq.consumer;

/**
 * @author Leach
 * @date 2017/4/10
 */
public abstract class CustomConsumer {
    private String topic;
    private String tag;

    public CustomConsumer(String topic, String tag) {
        this.topic = topic;
        this.tag = tag;
    }



    public abstract void consumeMessage(Object message);

    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }
}
