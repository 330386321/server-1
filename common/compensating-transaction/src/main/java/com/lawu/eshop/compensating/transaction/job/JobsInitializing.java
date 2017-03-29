package com.lawu.eshop.compensating.transaction.job;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author Leach
 * @date 2017/3/29
 */
public class JobsInitializing implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

        /**
         * 创建所有加了@CompensatingTransaction注解的任务
         */

    }
}
