package com.lawu.eshop.merchant.api.event;

import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.framework.web.interceptor.UserVisitEvent;
import com.lawu.eshop.framework.web.interceptor.UserVisitEventPublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author zhangyong
 * @date 2017/7/4.
 */
public class EventPublisher implements UserVisitEventPublish {
    @Autowired
    ApplicationContext applicationContext;

    public void publishLoginEvent(String userId) {
        applicationContext.publishEvent(new LoginEvent(this, userId));
    }

    @Override
    public void publishUserVisitEvent(String userNum,Long userId) {

        applicationContext.publishEvent(new UserVisitEvent(this, userNum, UserType.MERCHANT,userId));
    }
}
