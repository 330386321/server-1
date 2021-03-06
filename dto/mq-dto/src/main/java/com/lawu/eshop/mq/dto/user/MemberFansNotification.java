package com.lawu.eshop.mq.dto.user;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * @author meishuquan
 * @date 2017/9/21
 */
public class MemberFansNotification extends Notification {

    private static final long serialVersionUID = -1978940112436696614L;

    private String userNum;

    private String merchantStoreName;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getMerchantStoreName() {
        return merchantStoreName;
    }

    public void setMerchantStoreName(String merchantStoreName) {
        this.merchantStoreName = merchantStoreName;
    }
}
