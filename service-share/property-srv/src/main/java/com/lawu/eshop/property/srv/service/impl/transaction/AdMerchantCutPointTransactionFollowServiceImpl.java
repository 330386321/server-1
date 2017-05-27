package com.lawu.eshop.property.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.AdPointNotification;
import com.lawu.eshop.property.constants.MerchantTransactionTypeEnum;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.srv.service.PropertyInfoDataService;

/**
 * @author zhangrc
 * @date 2017/4/12
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_ME_CUT_POINT)
public class AdMerchantCutPointTransactionFollowServiceImpl extends AbstractTransactionFollowService<AdPointNotification, Reply> {

	    
	    @Autowired
	    private PropertyInfoDataService propertyInfoDataService;

	    @Override
	    @Transactional
	    public Reply execute(AdPointNotification notification) {
		    PropertyInfoDataParam param=new PropertyInfoDataParam();
		    param.setPoint(notification.getPoint().toString());
		    param.setUserNum(notification.getUserNum());
		    //param.setTransactionTitleEnum(TransactionTitleEnum.AD_RETURN_POINT);
		    param.setMerchantTransactionTypeEnum(MerchantTransactionTypeEnum.ADD_AD);
		    propertyInfoDataService.doHanlderMinusPoint(param);
	        return new Reply();
	    }
}
