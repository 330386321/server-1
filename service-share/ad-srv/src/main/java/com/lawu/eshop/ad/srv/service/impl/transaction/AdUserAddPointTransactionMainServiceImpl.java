package com.lawu.eshop.ad.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.srv.bo.AdPointNotification;
import com.lawu.eshop.ad.srv.constants.TransactionConstant;
import com.lawu.eshop.ad.srv.domain.PointPoolDO;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;
import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;

/**
 * @author zhangrc
 * @date 2017/4/12
 */
@Service("adUserAddPointTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.AD_USER_ADD_POINT, topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_USER_ADD_POINT)
public class AdUserAddPointTransactionMainServiceImpl extends AbstractTransactionMainService<AdPointNotification, Reply> {

	@Autowired
	private PointPoolDOMapper pointPoolDOMapper;

    @Override
    public AdPointNotification selectNotification(Long pointPoolId) {
    	 PointPoolDO  pointPoolDO =pointPoolDOMapper.selectByPrimaryKey(pointPoolId);
    	 AdPointNotification notification =new AdPointNotification();
    	 notification.setPoint(pointPoolDO.getPoint());
    	 notification.setUserNum(pointPoolDO.getMemberNum());
        return notification;
    }


}
