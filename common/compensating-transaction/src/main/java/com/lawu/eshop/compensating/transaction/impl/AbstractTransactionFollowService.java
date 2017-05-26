package com.lawu.eshop.compensating.transaction.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lawu.eshop.compensating.transaction.Notification;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionFollowService;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.mq.message.MessageProducerService;

/**
 * 补偿性事务从逻辑服务抽象类
 *
 * @author Leach
 * @date 2017/3/29
 */
public abstract class AbstractTransactionFollowService<N extends Notification, R extends Reply> implements TransactionFollowService<N, R> {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractTransactionFollowService.class);
	
    @Autowired
    private MessageProducerService messageProducerService;

    CompensatingTransactionFollow annotation = this.getClass().getAnnotation(CompensatingTransactionFollow.class);

    private String topic= annotation.topic();

    private String tags = annotation.tags() + "-reply";


    @Override
    public void receiveNotice(N notification) {
    	// 统一处理事务异常，手动捕捉异常，并且打印错误信息
    	try {
    		R reply = execute(notification);
    		if ( reply != null ) {
                reply.setTransactionId(notification.getTransactionId());
                sendCallback(reply);
            }
    	} catch (Exception e) {
    		logger.error(e.getMessage());
    	}
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
