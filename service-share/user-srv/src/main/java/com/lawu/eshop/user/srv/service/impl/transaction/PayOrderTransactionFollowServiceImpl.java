package com.lawu.eshop.user.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.user.srv.bo.PayOrderNotification;
import com.lawu.eshop.user.srv.mapper.extend.MerchantStoreDOMapperExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_ORDER_SRV, tags = "store")
public class PayOrderTransactionFollowServiceImpl extends AbstractTransactionFollowService<PayOrderNotification, Reply> {

    ;
    @Autowired
    private MerchantStoreDOMapperExtend merchantStoreDOMapperExtend;

    @Override
    public Reply execute(PayOrderNotification notification) {
        Long merchantId = notification.getMerchantId();
        //增加买单笔数
        merchantStoreDOMapperExtend.addMerchantStoreBuyNums(merchantId);
        return new Reply();
    }
}
