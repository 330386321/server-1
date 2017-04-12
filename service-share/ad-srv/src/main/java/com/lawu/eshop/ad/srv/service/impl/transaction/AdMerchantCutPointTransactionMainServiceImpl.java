package com.lawu.eshop.ad.srv.service.impl.transaction;

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
@Service("adMerchantCutPointTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.AD_ME_CUT_POINT, topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_ME_CUT_POINT)
public class AdMerchantCutPointTransactionMainServiceImpl extends AbstractTransactionMainService<AdPointNotification, Reply> {

	@Autowired
	private AdDOMapper adDOMapper;

    @Override
    public AdPointNotification selectNotification(Long adId) {
    	 AdDO ad=adDOMapper.selectByPrimaryKey(adId);
    	 AdPointNotification notification=new AdPointNotification();
    	 notification.setUserNum(ad.getMerchantNum());
    	 notification.setPoint(ad.getTotalPoint());
        return notification;
    }


}
