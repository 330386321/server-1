package com.lawu.eshop.mq.message;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.lawu.eshop.mq.consumer.CustomConsumerRegister;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * MQ消息者工厂ß
 *
 * @author Leach
 * @date 2017/4/9
 */
public class MQConsumerFactory {

    private DefaultMQPushConsumer consumer;

    private String namesrvAddr;

    private String instanceName;

    private String consumerGroup;

    private int pullBatchSize;

    private int consumeMessageBatchMaxSize;

    private Map<String, String> topicsTags;

    private MessageListenerConcurrently messageListenerConcurrently;

    @Autowired
    private CustomConsumerRegister customConsumerRegister;

    public void createDeviceDatagramConsumer() throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setInstanceName(instanceName);
        //设置为广播方式接受
        consumer.setMessageModel(MessageModel.BROADCASTING);
        //长轮询向broker拉取消息是批量拉取的， 默认设置批量的值为32
        consumer.setPullBatchSize(pullBatchSize);
        //监听器每次接受本地队列的消息是多少条，默认1个
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        if (topicsTags != null && !topicsTags.isEmpty()) {

            Iterator<Map.Entry<String, String>> iterator = topicsTags.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> topicTags = iterator.next();
                consumer.subscribe(topicTags.getKey(), topicTags.getValue());
            }
        }

        subscribeCustomConsumer();


        consumer.registerMessageListener(messageListenerConcurrently);
        consumer.start();
    }

    /**
     * 订阅自定义消费者涉及到的topic
     * @throws MQClientException
     */
    private void subscribeCustomConsumer() throws MQClientException {
        if (customConsumerRegister != null) {

            Map<String, List<String>> topics = customConsumerRegister.getTopics();
            Iterator<Map.Entry<String, List<String>>> topicIterator = topics.entrySet().iterator();
            while (topicIterator.hasNext()) {
                Map.Entry<String, List<String>> topic = topicIterator.next();
                List<String> tags = topic.getValue();
                
                String subExpression = tags.get(0);
                for (int i = 1; i < tags.size(); i++) {
                    subExpression += "|| " + tags.get(i);
                }
                
                String topicKey = topic.getKey();
                
                if (topicsTags != null && !topicsTags.isEmpty()) {
					if (topicsTags.containsKey(topicKey)) {
	                	subExpression +=  "|| " +  topicsTags.get(topicKey);
	                }
                }
                consumer.subscribe(topicKey, subExpression);
            }
        }
    }

    public void shutdown() {
        consumer.shutdown();
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public void setPullBatchSize(int pullBatchSize) {
        this.pullBatchSize = pullBatchSize;
    }

    public void setConsumeMessageBatchMaxSize(int consumeMessageBatchMaxSize) {
        this.consumeMessageBatchMaxSize = consumeMessageBatchMaxSize;
    }

    public void setTopicsTags(Map<String, String> topicsTags) {
        this.topicsTags = topicsTags;
    }

    public void setMessageListenerConcurrently(MessageListenerConcurrently messageListenerConcurrently) {
        this.messageListenerConcurrently = messageListenerConcurrently;
    }
}
