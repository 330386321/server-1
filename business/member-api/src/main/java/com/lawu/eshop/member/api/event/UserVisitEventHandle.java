package com.lawu.eshop.member.api.event;

import com.lawu.eshop.framework.core.event.AsyncEventHandle;
import com.lawu.eshop.framework.web.interceptor.UserVisitEvent;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/7/2
 */
@Component
public class UserVisitEventHandle implements AsyncEventHandle<UserVisitEvent> {

    @Override
    public void execute(UserVisitEvent event) {

        // 记录到缓存
    }
}
