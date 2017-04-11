package com.lawu.eshop.order.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.order.srv.bo.PayOrderNotification;
import com.lawu.eshop.order.srv.constants.TransactionConstant;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
@Service("payOrderTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.PAYORDER, topic = "transaction-payOrder", tags = "store")
public class PayOrderTransactionMainServiceImpl extends AbstractTransactionMainService<PayOrderNotification> {

    @Override
    public PayOrderNotification selectNotification(Long merchantId) {
        PayOrderNotification payOrderNotification = new PayOrderNotification();
        payOrderNotification.setMerchantId(merchantId);
        return payOrderNotification;
    }
}
