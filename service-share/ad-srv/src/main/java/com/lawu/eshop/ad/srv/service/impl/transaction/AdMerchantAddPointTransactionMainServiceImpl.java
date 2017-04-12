package com.lawu.eshop.ad.srv.service.impl.transaction;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.srv.bo.AdPointNotification;
import com.lawu.eshop.ad.srv.constants.TransactionConstant;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;

/**
 * @author zhangrc
 * @date 2017/4/12
 */
@Service("adMerchantAddPointTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.AD_ME_ADD_POINT, topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_ME_ADD_POINT)
public class AdMerchantAddPointTransactionMainServiceImpl extends AbstractTransactionMainService<AdPointNotification, Reply> {

	@Autowired
	private AdDOMapper adDOMapper;

    @Override
    public AdPointNotification selectNotification(Long adId) {
    	 AdDO ad=adDOMapper.selectByPrimaryKey(adId);
    	 AdPointNotification notification=new AdPointNotification();
    	 notification.setUserNum(ad.getMerchantNum());
    	 Integer hits=ad.getHits();
    	 BigDecimal point=ad.getPoint();
    	 notification.setPoint(point.multiply(new BigDecimal(hits)));
        return notification;
    }


}
