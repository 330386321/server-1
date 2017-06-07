package com.lawu.eshop.mq.dto.user;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * @author Leach
 * @date 2017/3/29
 */
public class RegNotification extends Notification {
	
	private static final long serialVersionUID = 5248272122861770528L;
	
	private String userNum;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
}
