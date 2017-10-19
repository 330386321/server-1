package com.lawu.eshop.ad.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.srv.constants.TransactionConstant;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.MemberAdRecordDO;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.ad.srv.mapper.MemberAdRecordDOMapper;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.AdPointNotification;

/**
 * @author zhangrc
 * @date 2017/4/12
 */
@Service("userClickAdTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.AD_CLICK_POINT, topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_USER_CLICK_POINT)
public class UserClickAdTransactionMainServiceImpl extends AbstractTransactionMainService<AdPointNotification, Reply> {

	@Autowired
	private MemberAdRecordDOMapper memberAdRecordDOMapper;
	
	@Autowired
	private AdDOMapper adDOMapper;

    @Override
    public AdPointNotification selectNotification(Long id) {
    	 MemberAdRecordDO memberAdRecordDO=memberAdRecordDOMapper.selectByPrimaryKey(id);
    	 
    	 AdDO ad = adDOMapper.selectByPrimaryKey(memberAdRecordDO.getAdId());
    	 AdPointNotification notification=new AdPointNotification();
    	 notification.setUserNum(memberAdRecordDO.getMemberNum());
    	 notification.setPoint(memberAdRecordDO.getOriginalPoint());
    	 notification.setAdId(memberAdRecordDO.getAdId());
    	 notification.setRegionPath(ad.getMerchantRegionPath());
    	 notification.setType(ad.getType());
    	 notification.setTitle(ad.getTitle());
        return notification;
    }


}
