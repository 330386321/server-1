package com.lawu.eshop.mq.dto.order;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
public class PayOrderNotification extends Notification {

	private static final long serialVersionUID = -3962777472789107260L;
	
	private Long payOrderId;

    public Long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
        this.payOrderId = payOrderId;
    }
}
