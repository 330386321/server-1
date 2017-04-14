package com.lawu.eshop.ad.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.srv.constants.TransactionConstant;
import com.lawu.eshop.ad.srv.domain.RedPacketDO;
import com.lawu.eshop.ad.srv.mapper.RedPacketDOMapper;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.AdPointNotification;

/**
 * @author zhangrc
 * @date 2017/4/12
 */
@Service("redPacketPointTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.RP_ME_CUT_POINT, topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_RP_ME_CUT_POINT)
public class RedPacketPointTransactionMainServiceImpl extends AbstractTransactionMainService<AdPointNotification, Reply> {

	@Autowired
	private RedPacketDOMapper redPacketDOMapper;

    @Override
    public AdPointNotification selectNotification(Long rpid) {
    	 RedPacketDO redPacketDO=redPacketDOMapper.selectByPrimaryKey(rpid);
    	 AdPointNotification notification=new AdPointNotification();
    	 notification.setUserNum(redPacketDO.getMerchantNum());
    	 notification.setPoint(redPacketDO.getTotlePoint());
         return notification;
    }

    @Override
    public void afterSuccess(Long relateId, Reply reply) {
    	 RedPacketDO redPacketDO=redPacketDOMapper.selectByPrimaryKey(relateId);
    	 redPacketDO.setStatus(new Byte("1"));
    	 redPacketDOMapper.updateByPrimaryKeySelective(redPacketDO);
        return;
    }
}
