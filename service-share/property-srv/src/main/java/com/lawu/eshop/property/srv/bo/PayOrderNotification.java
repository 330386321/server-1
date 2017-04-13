package com.lawu.eshop.property.srv.bo;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * @author zhangyong
 * @date 2017/4/13.
 */
public class PayOrderNotification extends Notification {

    private Long payOrderId;

    public Long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
        this.payOrderId = payOrderId;
    }

}
