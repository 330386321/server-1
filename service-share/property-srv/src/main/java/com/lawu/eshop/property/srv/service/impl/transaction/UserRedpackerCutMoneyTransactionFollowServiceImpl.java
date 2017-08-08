package com.lawu.eshop.property.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.ad.UserRedPacketNotification;
import com.lawu.eshop.property.constants.MemberTransactionTypeEnum;
import com.lawu.eshop.property.param.PropertyInfoDataParam;
import com.lawu.eshop.property.srv.service.impl.PropertyInfoDataServiceImpl;

/**
 * 扣除用户发红包金额
 * 
 * @author lihj
 * @date 2017年8月7日
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_AD_SRV, tags = MqConstant.TAG_AD_USER_REDPACKET_CUT_MONTY)
public class UserRedpackerCutMoneyTransactionFollowServiceImpl
		extends AbstractTransactionFollowService<UserRedPacketNotification, Reply> {

	@Autowired
	private PropertyInfoDataServiceImpl propertyInfoDataServiceImpl;

	@Override
	public void execute(UserRedPacketNotification notification) {
		PropertyInfoDataParam param = new PropertyInfoDataParam();
		param.setPoint(notification.getMoney().toString());
		param.setUserNum(notification.getUserNum());
		param.setMemberTransactionTypeEnum(MemberTransactionTypeEnum.USER_REDPACKET_CUT);
		propertyInfoDataServiceImpl.doHanlderMinusBalance(param);
	}
}
