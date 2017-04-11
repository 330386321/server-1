package com.lawu.eshop.user.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.user.srv.bo.PayOrderNotification;
import com.lawu.eshop.user.srv.mapper.extend.MerchantStoreDOMapperExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
@Service
@CompensatingTransactionFollow(topic = "transaction-payOrder", tags = "store")
public class PayOrderTransactionFollowServiceImpl extends AbstractTransactionFollowService<PayOrderNotification> {

    ;
    @Autowired
    private MerchantStoreDOMapperExtend merchantStoreDOMapperExtend;

    @Override
    public void execute(PayOrderNotification notification) {
        Long merchantId = notification.getMerchantId();
        //增加买单笔数
        merchantStoreDOMapperExtend.addMerchantStoreBuyNums(merchantId);

    }
}
