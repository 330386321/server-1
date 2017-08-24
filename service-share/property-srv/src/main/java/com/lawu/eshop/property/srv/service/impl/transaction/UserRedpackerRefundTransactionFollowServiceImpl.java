package com.lawu.eshop.property.srv.service.impl.transaction;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.property.MemberRedPacketBackNotification;
import com.lawu.eshop.property.constants.TransactionPayTypeEnum;
import com.lawu.eshop.property.param.MemberRedPacketRefundDataParam;
import com.lawu.eshop.property.srv.service.UserRedPacketService;

/**
 * 用户红包过期退款-从事务
 *
 * @author yangqh
 * @date 2017年8月21日
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_USER_REDPACKET_CANNEL_REFUND_MONEY)
public class UserRedpackerRefundTransactionFollowServiceImpl extends AbstractTransactionFollowService<MemberRedPacketBackNotification, Reply> {

    private Logger log = Logger.getLogger(UserRedpackerRefundTransactionFollowServiceImpl.class);

    @Autowired
    private UserRedPacketService userRedPacketService;

    @Override
    public void execute(MemberRedPacketBackNotification notification) {
        MemberRedPacketRefundDataParam param = new MemberRedPacketRefundDataParam();
        param.setRedPacketId(notification.getRedPacketId());
        param.setRefundMoney(notification.getRefundMoney());
        param.setUserNum(notification.getUserNum());
        param.setTradeNo(notification.getTradeNo());
        param.setTransactionPayTypeEnum(TransactionPayTypeEnum.getEnum(notification.getTransactionPayTypeEnum().getVal()));
        try {
            userRedPacketService.doRefund(param);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
