package com.lawu.eshop.merchant.api.event;

import com.lawu.eshop.framework.core.event.AsyncEventHandle;

/**
 * @author zhangyong
 * @date 2017/7/4.
 */
public class LoginEventHandle implements AsyncEventHandle<LoginEvent> {
    @Override
    public void execute(LoginEvent event) {
        System.out.println(event.getUserId());
    }
}
