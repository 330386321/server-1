package com.lawu.eshop.mq.dto.mall;


import com.lawu.eshop.compensating.transaction.Notification;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/7.
 */
public class CommentMerchantNotification extends Notification {

    @ApiModelProperty(value = "买单ID")
    private Long payOrderId;

    public Long getPayOrderId() {
        return payOrderId;
    }

    public void setPayOrderId(Long payOrderId) {
        this.payOrderId = payOrderId;
    }
}
