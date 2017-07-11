package com.lawu.eshop.user.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.property.StoreStatusNotification;
import com.lawu.eshop.mq.dto.user.MerchantStatusEnum;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;

/**
 * @author zhangyong
 * @date 2017/6/7.
 */
@Service("handleDepositEditStoreStatusTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_PROPERTY_SRV, tags = MqConstant.TAG_HANDLE_DEPOSIT)
public class HandleDepositEditStoreStatusTransactionFollowServiceImpl extends AbstractTransactionFollowService<StoreStatusNotification, Reply> {

	@Autowired
	private MerchantStoreInfoService merchantStoreInfoService;

	@Transactional
	@Override
	public void execute(StoreStatusNotification notification) {
		merchantStoreInfoService.updateMerchantStoreStatus(notification.getMerchantId(), MerchantStatusEnum.MERCHANT_STATUS_GIVE_MONEY_CHECK.val);
	}
}
