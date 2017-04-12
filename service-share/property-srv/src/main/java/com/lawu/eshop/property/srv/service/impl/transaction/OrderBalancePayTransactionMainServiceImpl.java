package com.lawu.eshop.property.srv.service.impl.transaction;

import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.property.srv.bo.BalancePayNotification;
import com.lawu.eshop.property.srv.constans.TransactionConstant;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月11日 下午7:58:19
 *
 */
@Service("orderBalancePayTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.ORDER_BALANCE_PAY, topic = "transaction-orderBalancePay", tags = "property")
public class OrderBalancePayTransactionMainServiceImpl extends AbstractTransactionMainService<BalancePayNotification> {

    @Override
    public BalancePayNotification selectNotification(Long id) {
    	BalancePayNotification balancePayNotification = new BalancePayNotification();
    	balancePayNotification.setId(id);
        return balancePayNotification;
    }


}
