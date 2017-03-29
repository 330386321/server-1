package com.lawu.eshop.compensating.transaction.impl;

import com.lawu.eshop.compensating.transaction.Notification;
import com.lawu.eshop.compensating.transaction.TransactionFollowService;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.mq.MqService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 补偿性事务从逻辑服务抽象类
 *
 * @author Leach
 * @date 2017/3/29
 */
public abstract class AbstractTransactionFollowService<V extends Notification> implements TransactionFollowService<V, Long> {

    @Autowired
    private MqService mqService;

    CompensatingTransactionFollow annotation = this.getClass().getAnnotation(CompensatingTransactionFollow.class);

    private String topic= annotation.topic();

    private String tags = annotation.tags();


    @Override
    public void receiveNotice(V notification) {
        execute(notification);
        sendCallback(notification.getTransactionId());

    }

    @Override
    public void sendCallback(Long transactionId) {

        mqService.sendMessage(topic, tags, transactionId);
    }

    /**
     * 接收到补偿性事务通知时需要处理的逻辑
     *
     * @param notification
     */
    public abstract void execute(V notification);
}
