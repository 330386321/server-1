package com.lawu.eshop.framework.web.interceptor;

import com.lawu.eshop.framework.core.event.AsyncEvent;
import com.lawu.eshop.framework.core.type.UserType;

/**
 * @author Leach
 * @date 2017/7/2
 */
public class UserVisitEvent extends AsyncEvent {

    private String userNum;

    private UserType userType;

    private Long userId;

    public UserVisitEvent(Object source, String userNum, UserType userType,Long userId) {
        super(source);
        this.userNum = userNum;
        this.userType = userType;
        this.userId = userId;
    }

    public String getUserNum() {
        return userNum;
    }

    public UserType getUserType() {
        return userType;
    }

    public Long getUserId() {
        return userId;
    }
}
