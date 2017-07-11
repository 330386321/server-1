package com.lawu.eshop.property.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.user.RegNotification;
import com.lawu.eshop.property.srv.service.PropertyInfoService;

/**
 * @author Leach
 * @date 2017/3/29
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_USER_SRV, tags = MqConstant.TAG_REG)
public class MemberRegTransactionFollowServiceImpl extends AbstractTransactionFollowService<RegNotification, Reply> {

	@Autowired
	private PropertyInfoService propertyInfoService;

	@Override
	public void execute(RegNotification notification) {
		propertyInfoService.savePropertyInfo(notification.getUserNum());
	}
}
