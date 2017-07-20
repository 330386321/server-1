package com.lawu.eshop.user.srv.mock.service;

import com.lawu.eshop.compensating.transaction.TransactionFollowService;
import org.springframework.stereotype.Service;

/**
 * @author meishuquan
 * @date 2017/7/12
 */
@Service
public class MockHandleDepositAuditCancelTransactionFollowService implements TransactionFollowService {


    @Override
    public void receiveNotice(Object notification) {

    }

    @Override
    public void sendCallback(Object reply) {

    }
}
