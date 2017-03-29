package com.lawu.eshop.property.srv.bo;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * @author Leach
 * @date 2017/3/29
 */
public class RegNotification extends Notification {
    private String userNum;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
}
