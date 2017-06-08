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
 * 核实操作成功后，发送消息修改门店状态为：待审核,并修改门店审核显示状态
 */
@Service("handleDepositAuditPassTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.HANDLE_DESPOISIT_AUDIT_PASS, topic = MqConstant.TOPIC_PROPERTY_SRV, tags = MqConstant.TAG_HANDLE_DEPOSIT_AUDIT_PASS)
public class HandleDepositAuditPassTransactionMainServiceImpl extends AbstractTransactionMainService<StoreStatusNotification, Reply> {
    @Override
    public StoreStatusNotification selectNotification(Long merchantId) {
        StoreStatusNotification notification = new StoreStatusNotification();
        notification.setMerchantId(merchantId);
        notification.setShow(true);
        return notification;
    }
}
