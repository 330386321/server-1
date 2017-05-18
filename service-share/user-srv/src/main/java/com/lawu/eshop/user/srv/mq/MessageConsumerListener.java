package com.lawu.eshop.user.srv.mq;

import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.user.FansInfo;
import com.lawu.eshop.mq.dto.user.HandleDepostMessage;
import com.lawu.eshop.mq.dto.user.MerchantStatusEnum;
import com.lawu.eshop.mq.dto.user.MessagePushInfo;
import com.lawu.eshop.mq.message.impl.AbstractMessageConsumerListener;
import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import com.lawu.eshop.user.constants.UserTypeEnum;
import com.lawu.eshop.user.srv.UserSrvConfig;
import com.lawu.eshop.user.srv.bo.*;
import com.lawu.eshop.user.srv.service.FansMerchantService;
import com.lawu.eshop.user.srv.service.MemberService;
import com.lawu.eshop.user.srv.service.MerchantService;
import com.lawu.eshop.user.srv.service.MerchantStoreInfoService;
import com.lawu.eshop.utils.GtPush;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Autowired
    private UserSrvConfig userSrvConfig;

    @Autowired
    private FansMerchantService fansMerchantService;

    @Override
    public void consumeMessage(String topic, String tags, Object message) {

        if(MqConstant.TOPIC_ORDER_SRV.equals(topic) && MqConstant.TAG_BUY_NUMBERS.equals(tags)){
            //增加买单笔数
            FansInfo fansInfo  = (FansInfo) message;
            merchantStoreInfoService.addMerchantStoreBuyNums(fansInfo.getMerchantId());
            //成为粉丝
            FansMerchantBO fansMerchantBO = fansMerchantService.getFansMerchant(fansInfo.getMemberId(),fansInfo.getMerchantId());
            if(fansMerchantBO == null){
                //买单增加粉丝
                fansMerchantService.saveFansMerchant(fansInfo.getMerchantId(),fansInfo.getMemberId(), FansMerchantChannelEnum.PAY);
            }

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
        } else if (MqConstant.TOPIC_PROPERTY_SRV.equals(topic) && MqConstant.TAG_HANDLE_DEPOSIT.equals(tags)){
            //根据商家编号查询商家
            HandleDepostMessage info = (HandleDepostMessage) message;
            MerchantBO merchantBO = merchantService.findMemberByNum(info.getUserNum());
            //修改门店状态
            merchantStoreInfoService.updateMerchantStoreStatus(merchantBO.getId(), info.getStatusEnum().val);
            if(info.getStatusEnum().val == MerchantStatusEnum.MERCHANT_STATUS_CANCEL.val){
                //查询门店信息
                MerchantStoreInfoBO storeInfoBO= merchantStoreInfoService.selectMerchantStoreByMId(merchantBO.getId());
                if(storeInfoBO == null){
                    return;
                }
                //删除solr门店信息
                SolrUtil.delSolrDocsById(storeInfoBO.getMerchantStoreId(),userSrvConfig.getSolrUrl(), userSrvConfig.getSolrMerchantCore());
            }
        }else if (MqConstant.TOPIC_MALL_SRV.equals(topic) && MqConstant.TAG_GTPUSHALL.equals(tags)){
            //发送推送消息
            MessagePushInfo info = (MessagePushInfo) message;
            GtPush push = new GtPush();
           if(UserTypeEnum.MEMBER.val==info.getUserType()){
               //推送所有
               push.pushToAllUser(info.getTitle(),info.getContent());
           }else{
               push.pushToAllCompany(info.getTitle(),info.getContent());
           }
        }else if (MqConstant.TOPIC_MALL_SRV.equals(topic) && MqConstant.TAG_GTPUSH_AREA.equals(tags)){
            //发送推送消息
            MessagePushInfo info = (MessagePushInfo) message;
            GtPush push = new GtPush();
            List<MessagePushBO> messagePushBOS = null;
            if(UserTypeEnum.MEMBER.val==info.getUserType()){
                //推送用户
                messagePushBOS = memberService.findMessagePushList(info.getArea());
                for(MessagePushBO messagePushBO : messagePushBOS){
                    push.sendMessageToCidCustoms(info.getContent(),messagePushBO.getGtCid(),info.getTitle());
                }
            }else{
                messagePushBOS = merchantService.findMessagePushList(info.getArea());
                for(MessagePushBO messagePushBO : messagePushBOS){
                    push.sendMessageToCid(info.getContent(),messagePushBO.getGtCid(),info.getTitle());
                }
            }


        }
    }
}
