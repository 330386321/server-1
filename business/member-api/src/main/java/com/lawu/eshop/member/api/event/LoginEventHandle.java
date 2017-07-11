package com.lawu.eshop.member.api.event;

import com.lawu.eshop.framework.core.event.AsyncEventHandle;
import org.springframework.stereotype.Component;

/**
 * @author Leach
 * @date 2017/6/29
 */
@Component
public class LoginEventHandle implements AsyncEventHandle<LoginEvent> {
    @Override
    public void execute(LoginEvent event) {
        System.out.println(event.getUserId());
    }
}
