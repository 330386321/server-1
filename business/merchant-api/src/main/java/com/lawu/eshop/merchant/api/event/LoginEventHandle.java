package com.lawu.eshop.merchant.api.event;

import com.lawu.eshop.framework.core.event.AsyncEventHandle;
import org.springframework.stereotype.Component;

/**
 * @author zhangyong
 * @date 2017/7/4.
 */
@Component
public class LoginEventHandle implements AsyncEventHandle<LoginEvent> {
    @Override
    public void execute(LoginEvent event) {
        System.out.println(event.getUserId());
    }
}
