package com.lawu.eshop.property.srv.service;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
public interface InviteFansService {

    /**
     * 邀请粉丝消费积分
     *
     * @param userNum      商户编号
     * @param consumePoint 消费积分
     */
    void inviteFans(String userNum, Integer consumePoint);
}
