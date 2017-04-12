package com.lawu.eshop.mall.srv.bo;


import com.lawu.eshop.compensating.transaction.Notification;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
public class CommentProductNotification extends Notification {
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
