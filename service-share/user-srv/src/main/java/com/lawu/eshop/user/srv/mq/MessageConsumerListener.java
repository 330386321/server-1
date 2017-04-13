package com.lawu.eshop.user.srv.mq;

import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.message.impl.AbstractMessageConsumerListener;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Leach
 * @date 2017/4/11
 */
public class MessageConsumerListener extends AbstractMessageConsumerListener {

    @Autowired
    private MerchantStoreInfoService merchantStoreInfoService;
    @Override
    public void consumeMessage(String topic, String tags, Object message) {

        if(MqConstant.TOPIC_ORDER_SRV.equals(topic) && MqConstant.TAG_BUY_NUMBERS.equals(tags)){
            //增加买单笔数
            Long merchantId = Long.valueOf(message.toString());
            merchantStoreInfoService.addMerchantStoreBuyNums(merchantId);

        }
    }
}
