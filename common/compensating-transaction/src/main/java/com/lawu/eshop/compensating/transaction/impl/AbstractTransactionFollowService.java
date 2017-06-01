package com.lawu.eshop.compensating.transaction.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Notification;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.TransactionFollowService;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.mq.message.MessageProducerService;
import com.lawu.eshop.synchronization.lock.service.FollowTransactionRecordService;
import com.lawu.eshop.synchronization.lock.service.LockService;

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

    private String topic = annotation.topic();

    private String tags = annotation.tags() + "-reply";
    
    @Autowired
    private LockService lockService;
    
    @Autowired
    private FollowTransactionRecordService followTransactionRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveNotice(N notification) {
    	// 统一处理事务异常，手动捕捉异常，并且打印错误信息
    	StringBuilder locakName = new StringBuilder();
		locakName.append(topic).append("_").append(annotation.tags()).append("_").append(notification.getTransactionId());
		
		// 从事务幂等性保证，表中存在记录，说明消息已经被成功消费，直接返回
		if (followTransactionRecordService.isExist(topic, notification.getTransactionId())) {
			logger.info("消息已经被消费");
			return;
		}
		
		// 如果没有获取到锁直接返回
		if (!lockService.tryLock(locakName.toString())) {
			logger.info("锁还未释放");
			return;
		}
		
    	try {
    		
    		R reply = execute(notification);
    		
    		// 如果消息被成功消费，保存一条消费记录
    		followTransactionRecordService.consumptionSuccessful(topic, notification.getTransactionId());
    		
    		// 只有事务全部执行成功，才会发送回复消息
    		if ( reply != null ) {
                reply.setTransactionId(notification.getTransactionId());
                sendCallback(reply);
            }
    		
    	} catch (Exception e) {
    		logger.error("事务执行异常", e);
    		// 抛出异常，回滚事务
    		throw e;
    	}
    	
    	//事务执行完成释放锁
		lockService.unLock(locakName.toString());
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
