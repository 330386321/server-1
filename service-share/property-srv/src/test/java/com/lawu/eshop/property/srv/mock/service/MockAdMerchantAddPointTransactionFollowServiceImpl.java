package com.lawu.eshop.property.srv.mock.service;/**
 * Created by ${Yangqh} on 2017/7/12.
 */

import com.lawu.eshop.compensating.transaction.TransactionFollowService;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mq.dto.ad.AdPointNotification;
import com.lawu.eshop.property.srv.service.TransactionService;
import com.lawu.eshop.property.srv.service.impl.transaction.AdMerchantAddPointTransactionFollowServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p> </p>
 *
 * @author Yangqh
 * @date 2017/7/12 11:19
 */
@Service
public class MockAdMerchantAddPointTransactionFollowServiceImpl implements TransactionFollowService {
    @Override
    public void receiveNotice(Object notification) {

    }

    @Override
    public void sendCallback(Object reply) {

    }
}
