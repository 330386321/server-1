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

    public UserVisitEvent(Object source, String userNum, UserType userType) {
        super(source);
        this.userNum = userNum;
        this.userType = userType;
    }

    public String getUserNum() {
        return userNum;
    }

    public UserType getUserType() {
        return userType;
    }
}
