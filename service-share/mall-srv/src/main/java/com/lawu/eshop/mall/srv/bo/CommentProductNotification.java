package com.lawu.eshop.mall.srv.bo;


import com.lawu.eshop.compensating.transaction.Notification;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
public class CommentProductNotification extends Notification {

    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
