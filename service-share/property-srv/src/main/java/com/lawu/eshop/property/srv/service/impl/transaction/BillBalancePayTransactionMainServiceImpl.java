package com.lawu.eshop.property.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.property.srv.bo.BalancePayNotification;
import com.lawu.eshop.property.srv.bo.BillBalancePayNotification;
import com.lawu.eshop.property.srv.constans.TransactionConstant;

/**
 * 
 * <p>
 * Description: 
 * </p>
 * @author Yangqh
 * @date 2017年4月12日 上午9:58:13
 *
 */
//@Service("billBalancePayTransactionMainServiceImpl")
//@CompensatingTransactionMain(value = TransactionConstant.BILL_BALANCE_PAY, topic = "transaction-BillBalancePay", tags = "property")
public class BillBalancePayTransactionMainServiceImpl /*extends AbstractTransactionMainService<BillBalancePayNotification>*/ {

//    @Override
//    public BillBalancePayNotification selectNotification(Long id) {
//    	BillBalancePayNotification balancePayNotification = new BillBalancePayNotification();
//    	balancePayNotification.setId(id);
//        return balancePayNotification;
//    }


}
