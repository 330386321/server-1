package com.lawu.eshop.compensating.transaction.impl;

import com.lawu.eshop.compensating.transaction.Notification;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionFollowService;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.mq.message.MessageProducerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 补偿性事务从逻辑服务抽象类
 *
 * @author Leach
 * @date 2017/3/29
 */
public abstract class AbstractTransactionFollowService<N extends Notification, R extends Reply> implements TransactionFollowService<N, R> {

    @Autowired
    private MessageProducerService messageProducerService;

    CompensatingTransactionFollow annotation = this.getClass().getAnnotation(CompensatingTransactionFollow.class);

    private String topic= annotation.topic();

    private String tags = annotation.tags();


    @Override
    public void receiveNotice(N notification) {
        R reply = execute(notification);
        reply.setTransactionId(notification.getTransactionId());
        sendCallback(reply);

    }

    @Override
    public void sendCallback(R reply) {

        messageProducerService.sendMessage(topic, tags, reply);
    }

    /**
     * 接收到补偿性事务通知时需要处理的逻辑
     *
     * @param notification
     */
    public abstract R execute(N notification);
}
