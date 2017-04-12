package com.lawu.eshop.compensating.transaction;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.mq.consumer.CustomConsumer;
import com.lawu.eshop.mq.consumer.CustomConsumerRegister;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Leach
 * @date 2017/3/29
 */
@Component
public class TransactionInitializing implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private CustomConsumerRegister customConsumerRegister;

    @Override
    public void afterPropertiesSet() throws Exception {

        //customConsumerRegister = SpringApplicationContextHolder.getBean(CustomConsumerRegister.class);

        if (customConsumerRegister == null) {
            return;
        }

        /**
         * 创建所有加了@CompensatingTransactionMain注解service对应的消费者
         */
        Map<String, Object> mainTransactionServiceBeans = applicationContext.getBeansWithAnnotation(CompensatingTransactionMain.class);


        Iterator<Map.Entry<String, Object>> mainIterator = mainTransactionServiceBeans.entrySet().iterator();
        while (mainIterator.hasNext()) {
            Map.Entry<String, Object> mainTransactionServiceBean = mainIterator.next();

            Advised advised = (Advised) applicationContext.getBean(mainTransactionServiceBean.getKey());
            SingletonTargetSource singTarget = (SingletonTargetSource) advised.getTargetSource();
            TransactionMainService transactionMainService = (TransactionMainService) singTarget.getTarget();

            CompensatingTransactionMain annotation = transactionMainService.getClass().getAnnotation(CompensatingTransactionMain.class);

            customConsumerRegister.registerConsumers(new CustomConsumer(annotation.topic(), annotation.tags()) {
                @Override
                public void consumeMessage(Object message) {
                    transactionMainService.receiveCallback(message);
                }
            });

        }


        /**
         * 创建所有加了@CompensatingTransactionFollow注解service对应的消费者
         */
        Map<String, Object> followTransactionServiceBeans = applicationContext.getBeansWithAnnotation(CompensatingTransactionFollow.class);

        Iterator<Map.Entry<String, Object>> followIterator = followTransactionServiceBeans.entrySet().iterator();
        while (followIterator.hasNext()) {
            Map.Entry<String, Object> followTransactionServiceBean = followIterator.next();

            TransactionFollowService transactionFollowService = (TransactionFollowService) followTransactionServiceBean.getValue();

            CompensatingTransactionFollow annotation = transactionFollowService.getClass().getAnnotation(CompensatingTransactionFollow.class);

            customConsumerRegister.registerConsumers(new CustomConsumer(annotation.topic(), annotation.tags()) {
                @Override
                public void consumeMessage(Object message) {
                    transactionFollowService.receiveNotice((Notification) message);
                }
            });

        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
