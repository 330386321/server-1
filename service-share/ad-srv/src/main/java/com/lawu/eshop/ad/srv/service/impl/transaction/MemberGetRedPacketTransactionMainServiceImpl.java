package com.lawu.eshop.ad.srv.service.impl.transaction;

import com.lawu.eshop.ad.srv.constants.TransactionConstant;
import com.lawu.eshop.ad.srv.domain.UserTakedRedPacketDO;
import com.lawu.eshop.ad.srv.mapper.UserTakedRedPacketDOMapper;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.UserRedPacketNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户领取红包（用户发的红包）-主事务
 * 给用户加余额
 * @author yangqh
 * @date 2017年8月21日
 */
@Service("memberGetRedPacketTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.USER_REDPACKED_GET_MONEY, topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_USER_GET_AD_USER_REDPACKET)
public class MemberGetRedPacketTransactionMainServiceImpl extends AbstractTransactionMainService<UserRedPacketNotification, Reply> {

    @Autowired
    private UserTakedRedPacketDOMapper userTakedRedPacketDOMapper;

    @Override
    public UserRedPacketNotification selectNotification(Long userTakedRedPacketId) {

        UserTakedRedPacketDO userTakedRedPacketDO = userTakedRedPacketDOMapper.selectByPrimaryKey(userTakedRedPacketId);
        UserRedPacketNotification notification = new UserRedPacketNotification();
        notification.setId(userTakedRedPacketDO.getUserRedPackId());
        notification.setMoney(userTakedRedPacketDO.getMoney());
        notification.setUserNum(userTakedRedPacketDO.getUserNum());
        return notification;
    }
}
