package com.lawu.eshop.user.srv.mq;

import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.message.impl.AbstractMessageConsumerListener;
import com.lawu.eshop.user.param.MessagePushInfo;
import com.lawu.eshop.user.srv.bo.MemberBO;
import com.lawu.eshop.user.srv.bo.MerchantBO;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.user.srv.service.MerchantService;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import com.lawu.eshop.utils.GtPush;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Leach
 * @date 2017/4/11
 */
public class MessageConsumerListener extends AbstractMessageConsumerListener {

    @Autowired
    private MerchantStoreInfoService merchantStoreInfoService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MerchantService merchantService;
    @Override
    public void consumeMessage(String topic, String tags, Object message) {

        if(MqConstant.TOPIC_ORDER_SRV.equals(topic) && MqConstant.TAG_BUY_NUMBERS.equals(tags)){
            //增加买单笔数
            Long merchantId = Long.valueOf(message.toString());
            merchantStoreInfoService.addMerchantStoreBuyNums(merchantId);

        }else if (MqConstant.TOPIC_MALL_SRV.equals(topic) && MqConstant.TAG_GTPUSH.equals(tags)){
            //发送推送消息
            MessagePushInfo info = (MessagePushInfo) message;
            if(info.getUserNum().contains("M")){
              MemberBO memberBO =  memberService.findMemberByNum(info.getUserNum());
                //会员单个推送
                GtPush push = new GtPush();
                push.sendMessageToCidCustoms(info.getContent(),memberBO.getGtCid(),info.getTitle());
            }else {
                //商家单个推送
                MerchantBO merchantBO = merchantService.findMemberByNum(info.getUserNum());
                GtPush push = new GtPush();
                push.sendMessageToCid(info.getContent(),merchantBO.getGtCid(),info.getTitle());
            }
        }
    }
}
