package com.lawu.eshop.compensating.transaction.impl;

import com.lawu.eshop.compensating.transaction.Notification;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.compensating.transaction.TransactionStatusService;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.mq.message.MessageProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 补偿性事务主逻辑服务抽象类
 * @author Leach
 * @date 2017/3/29
 */
public abstract class AbstractTransactionMainService<T extends Notification> implements TransactionMainService<Long> {

    @Autowired
    private MessageProducerService messageProducerService;

    @Autowired
    private TransactionStatusService transactionStatusService;

    private CompensatingTransactionMain annotation = this.getClass().getAnnotation(CompensatingTransactionMain.class);

    private byte type = annotation.value();

    private String topic = annotation.topic();

    private String tags = annotation.tags();

    /**
     * 查询需要发送到其他模块的数据
     *
     * @param relateId
     * @return
     */
    public abstract T selectNotification(Long relateId);

    /**
     * 事务成功回调时，需要执行的逻辑
     * 默认为空，需要的话可以Override
     * 
     * @param relateId
     */
    public void afterSuccess(Long relateId) {
        return;
    }

    @Override
    public void sendNotice(Long relateId) {

        T notification = selectNotification(relateId);

        if (notification == null) {
            throw new IllegalArgumentException("Can't find the notification by relateId: " + relateId);
        }
        Long transactionId = transactionStatusService.save(relateId, type);
        notification.setTransactionId(transactionId);

        messageProducerService.sendMessage(topic, tags, notification);
    }

    @Transactional
    @Override
    public void receiveCallback(Long reply) {
        Long relateId = transactionStatusService.success(reply);
        afterSuccess(relateId);
    }

    @Override
    public void check() {
        List<Long> notDoneList = transactionStatusService.selectNotDoneList(type);
        for (int i = 0; i < notDoneList.size(); i++) {
            selectNotification(notDoneList.get(i));
        }
    }

}
