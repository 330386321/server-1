package com.lawu.eshop.ad.srv.service.impl.transaction;

import com.lawu.eshop.ad.param.UserRedPacketUpdateParam;
import com.lawu.eshop.ad.srv.service.UserRedPacketService;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.property.AdPaymentNotification;
import com.lawu.eshop.mq.dto.property.MemberRedPacketPaymentNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商家发广告异步回调后修改广告记录-从事务
 */
@Service("memberRedPacketPaymentTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_PROPERTY_SRV, tags = MqConstant.TAG_HANDLE_AD)
public class MerchantAdPaymentTransactionFollowServiceImpl extends AbstractTransactionFollowService<AdPaymentNotification, Reply> {

    @Autowired
    private UserRedPacketService userRedPacketService;

    /**
     * 接收资产模块支付购物订单时发送的消息
     */
    @Transactional
    @Override
    public void execute(AdPaymentNotification notification) {
        UserRedPacketUpdateParam param = new UserRedPacketUpdateParam();
        param.setPayType(notification.getPaymentMethod());
        //param.setRedId(Long.valueOf(notification.getRedPacketId()));
        param.setThirdNum(notification.getThirdNumber());
        userRedPacketService.updateUserPacketInfo(param);
    }
}