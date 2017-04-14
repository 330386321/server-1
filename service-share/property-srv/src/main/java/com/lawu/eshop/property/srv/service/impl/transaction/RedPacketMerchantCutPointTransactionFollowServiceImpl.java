package com.lawu.eshop.property.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.AdPointNotification;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.constants.TransactionTitleEnum;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.srv.service.PropertyInfoDataService;

/**
 * @author zhangrc
 * @date 2017/4/12
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_RP_ME_CUT_POINT)
public class RedPacketMerchantCutPointTransactionFollowServiceImpl extends AbstractTransactionFollowService<AdPointNotification, Reply> {

	    
	    @Autowired
	    private PropertyInfoDataService propertyInfoDataService;

	    @Override
	    public Reply execute(AdPointNotification notification) {
		    PropertyInfoDataParam param=new PropertyInfoDataParam();
		    param.setPoint(notification.getPoint().toString());
		    param.setUserNum(notification.getUserNum());
		    param.setTransactionTitleEnum(TransactionTitleEnum.ADD_RED_PACKET);
		    param.setMerchantTransactionTypeEnum(MerchantTransactionTypeEnum.ADD_RED_PACKET);
		    propertyInfoDataService.doHanlderMinusPoint(param);
	        return new Reply();
	    }
}
