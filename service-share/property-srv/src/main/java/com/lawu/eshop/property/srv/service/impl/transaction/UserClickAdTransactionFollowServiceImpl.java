package com.lawu.eshop.property.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.AdPointNotification;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.srv.service.AdService;

/**
 * @author zhangrc
 * @date 2017/4/12
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_USER_ADD_POINT)
public class UserClickAdTransactionFollowServiceImpl extends AbstractTransactionFollowService<AdPointNotification, Reply> {

    
    @Autowired
    private AdService adService;

    @Override
    public Reply execute(AdPointNotification notification) {
	    PropertyInfoDataParam param=new PropertyInfoDataParam();
	    param.setPoint(notification.getPoint().toString());
	    param.setUserNum(notification.getUserNum());
	    param.setTransactionTitleEnum(TransactionTitleEnum.CLICK_AD);
	    param.setMemberTransactionTypeEnum(MemberTransactionTypeEnum.ADVERTISING);
	    adService.clickAd(param);
        return new Reply();
    }
}
