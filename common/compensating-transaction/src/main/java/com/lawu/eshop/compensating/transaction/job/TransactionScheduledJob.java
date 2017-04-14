package com.lawu.eshop.compensating.transaction.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Leach
 * @date 2017/3/29
 */
public class TransactionScheduledJob implements SimpleJob, InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private List<TransactionMainService> transactionMainServiceList = new ArrayList<>();

    @Override
    public void execute(ShardingContext shardingContext) {
        for (int i = 0; i < transactionMainServiceList.size(); i++) {
            transactionMainServiceList.get(i).check();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        /**
         * 创建所有加了@CompensatingTransactionMain注解service对应的消费者
         */
        Map<String, Object> mainTransactionServiceBeans = applicationContext.getBeansWithAnnotation(CompensatingTransactionMain.class);

        Iterator<Map.Entry<String, Object>> mainIterator = mainTransactionServiceBeans.entrySet().iterator();
        while (mainIterator.hasNext()) {
            Map.Entry<String, Object> mainTransactionServiceBean = mainIterator.next();

            TransactionMainService transactionMainService = (TransactionMainService) mainTransactionServiceBean.getValue();

            transactionMainServiceList.add(transactionMainService);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
