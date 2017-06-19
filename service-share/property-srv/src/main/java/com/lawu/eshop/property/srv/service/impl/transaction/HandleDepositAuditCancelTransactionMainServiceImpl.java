package com.lawu.eshop.property.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.property.StoreStatusNotification;
import com.lawu.eshop.property.srv.constans.TransactionConstant;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong
 * @date 2017/6/7.
 * 退款成功操作后，发送消息修改门店状态为：注销
 */
@Service("handleDepositAuditCancelTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.HANDLE_DESPOISIT_AUDIT_CANCEL, topic = MqConstant.TOPIC_PROPERTY_SRV, tags = MqConstant.TAG_HANDLE_DEPOSIT_AUDIT_CANCEL)
public class HandleDepositAuditCancelTransactionMainServiceImpl extends AbstractTransactionMainService<StoreStatusNotification, Reply> {
    @Override
    public StoreStatusNotification selectNotification(Long merchantId) {
        StoreStatusNotification notification = new StoreStatusNotification();
        notification.setMerchantId(merchantId);
        notification.setShow(false);
        return notification;
    }
}