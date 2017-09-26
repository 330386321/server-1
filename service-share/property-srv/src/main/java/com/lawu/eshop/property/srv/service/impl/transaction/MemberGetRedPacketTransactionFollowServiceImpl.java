package com.lawu.eshop.property.srv.service.impl.transaction;

import com.lawu.eshop.property.constants.LoveTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.UserRedPacketNotification;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.srv.service.PropertyInfoDataService;

/**
 * 用户领取红包（用户发的红包）-从事务
 * 给用户加余额
 *
 * @author yangqh
 * @date 2017年8月21日
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_USER_GET_AD_USER_REDPACKET)
public class MemberGetRedPacketTransactionFollowServiceImpl extends AbstractTransactionFollowService<UserRedPacketNotification, Reply> {

    @Autowired
    private PropertyInfoDataService propertyInfoDataService;

    @Override
    public void execute(UserRedPacketNotification notification) {
        PropertyInfoDataParam param = new PropertyInfoDataParam();
        param.setPoint(notification.getMoney().toString());
        param.setUserNum(notification.getUserNum());
        param.setMemberTransactionTypeEnum(MemberTransactionTypeEnum.RED_SWEEP);
        param.setLoveTypeEnum(LoveTypeEnum.RED_PACKAGE);
        param.setTempBizId("0");
        propertyInfoDataService.doHanlderBalanceIncome(param);
    }
}
