package com.lawu.eshop.ad.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.srv.constants.TransactionConstant;
import com.lawu.eshop.ad.srv.domain.TakePlatformRedPacketDO;
import com.lawu.eshop.ad.srv.mapper.TakePlatformRedPacketDOMapper;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.UserTakePlatRedPacketNotification;

/**
 * 用户领取平台红包事物
 * 
 * @author zhangrc
 * @date 2017/10/19
 */
@Service("userTakePlatRedTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.USER_TKAE_PLAT_MONEY, topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_USER_TAKE_PLAT_RED)
public class UserTakePlatRedTransactionMainServiceImpl extends AbstractTransactionMainService<UserTakePlatRedPacketNotification, Reply> {
	
	@Autowired
	private TakePlatformRedPacketDOMapper takePlatformRedPacketDOMapper;

    @Override
    public UserTakePlatRedPacketNotification selectNotification(Long id) {
    	TakePlatformRedPacketDO takePlatformRedPacketDO = takePlatformRedPacketDOMapper.selectByPrimaryKey(id);
    	UserTakePlatRedPacketNotification notification=new UserTakePlatRedPacketNotification();
    	notification.setUserNum(takePlatformRedPacketDO.getUserNum());
    	notification.setMoney(takePlatformRedPacketDO.getPoint());
    	notification.setId(takePlatformRedPacketDO.getId());
        return notification;
    }


}
