package com.lawu.eshop.member.api.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/6/29
 */
@Component
public class EventPublisher {

    @Autowired
    ApplicationContext applicationContext;

    public void publishLoginEvent(String userId) {
        applicationContext.publishEvent(new LoginEvent(this, userId));
    }

}
