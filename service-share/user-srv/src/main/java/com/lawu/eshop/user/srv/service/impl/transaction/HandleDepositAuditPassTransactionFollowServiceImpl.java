package com.lawu.eshop.user.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.property.StoreStatusNotification;
import com.lawu.eshop.mq.dto.user.MerchantStatusEnum;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangyong
 * @date 2017/6/7.
 */
@Service("handleDepositAuditPassTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_PROPERTY_SRV, tags = MqConstant.TAG_HANDLE_DEPOSIT_AUDIT_PASS)
public class HandleDepositAuditPassTransactionFollowServiceImpl extends AbstractTransactionFollowService<StoreStatusNotification, Reply> {
    @Autowired
    private MerchantStoreInfoService merchantStoreInfoService;
    /**
     *
     */
    @Transactional
    @Override
    public Reply execute(StoreStatusNotification notification) {
        Reply rtn = null;

        if (notification == null) {
            return rtn;
        }

        rtn = new Reply();
        merchantStoreInfoService.updateMerchantStoreStatus(notification.getMerchantId(), MerchantStatusEnum.MERCHANT_STATUS_UNCHECK.val);

        return rtn;
    }
}
