package com.lawu.eshop.mall.srv.mock.service;

import com.lawu.eshop.compensating.transaction.TransactionMainService;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong
 * @date 2017/7/12.
 */
@Service("orderCommentMerchantTransactionMainServiceImpl")
public class MockOrderCommentMerchantTransactionMainService implements TransactionMainService {
    @Override
    public void sendNotice(Long relateId) {

    }

    @Override
    public void receiveCallback(Object reply) {

    }

    @Override
    public void check(Long count) {

    }

    @Override
    public String getTopic() {
        return null;
    }
}
