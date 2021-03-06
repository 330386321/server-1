package com.lawu.eshop.mq.dto.user;

import java.math.BigDecimal;

import com.lawu.eshop.compensating.transaction.Notification;

/**
 * @author meishuquan
 * @date 2017/9/21
 */
public class MerchantFansNotification extends Notification {

    private static final long serialVersionUID = -5510006840412283784L;

    private String userNum;

    private BigDecimal point;

    private Long bizId;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }
}
