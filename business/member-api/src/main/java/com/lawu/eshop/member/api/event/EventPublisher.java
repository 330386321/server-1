package com.lawu.eshop.member.api.event;

import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.framework.web.interceptor.UserVisitEvent;
import com.lawu.eshop.framework.web.interceptor.UserVisitEventPublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/6/29
 */
@Component
public class EventPublisher implements UserVisitEventPublish {

    @Autowired
    ApplicationContext applicationContext;

    public void publishLoginEvent(String userId) {
        applicationContext.publishEvent(new LoginEvent(this, userId));
    }

    @Override
    public void publishUserVisitEvent(String userNum,Long userId) {

        applicationContext.publishEvent(new UserVisitEvent(this, userNum, UserType.MEMBER,userId));
    }
}