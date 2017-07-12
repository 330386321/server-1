package com.lawu.eshop.property.srv.mock.service;/**
 * Created by ${Yangqh} on 2017/7/12.
 */

import com.lawu.eshop.compensating.transaction.TransactionMainService;
import org.springframework.stereotype.Service;

/**
 * <p> </p>
 *
 * @author Yangqh
 * @date 2017/7/12 11:13
 */
@Service("payOrderTransactionMainServiceImpl")
public class MockPayOrderTransactionMainService implements TransactionMainService {

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
