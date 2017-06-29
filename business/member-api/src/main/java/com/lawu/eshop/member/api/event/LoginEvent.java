package com.lawu.eshop.member.api.event;

import com.lawu.eshop.framework.core.event.AsyncEvent;

/**
 * @author Leach
 * @date 2017/6/29
 */
public class LoginEvent extends AsyncEvent {

    private String userId;

    /**
     * Create a new ApplicationEvent.
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LoginEvent(Object source, String userId) {
        super(source);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
