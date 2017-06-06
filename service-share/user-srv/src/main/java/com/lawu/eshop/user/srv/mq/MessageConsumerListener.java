package com.lawu.eshop.user.srv.mq;

import com.gexin.fastjson.JSONObject;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.user.*;
import com.lawu.eshop.mq.message.impl.AbstractMessageConsumerListener;
import com.lawu.eshop.solr.SolrUtil;
import com.lawu.eshop.user.constants.FansMerchantChannelEnum;
import com.lawu.eshop.user.constants.UserTypeEnum;
import com.lawu.eshop.user.srv.UserSrvConfig;
import com.lawu.eshop.user.srv.bo.*;
import com.lawu.eshop.user.srv.service.*;
import com.lawu.eshop.utils.GtPush;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Leach
 * @date 2017/4/11
 */
public class MessageConsumerListener extends AbstractMessageConsumerListener {
    private static Logger logger = LoggerFactory.getLogger(MessageConsumerListener.class);
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

    @Autowired
    private MerchantAuditService merchantAuditService;

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
            JSONObject jobj = new JSONObject();
            jobj.put("title",info.getTitle());
            jobj.put("content",info.getContent());
            jobj.put("type", MessageTypeEnum.getEnum(info.getMessageType()));
            if(info.getUserNum().contains("M")){
              MemberBO memberBO =  memberService.findMemberByNum(info.getUserNum());
                //会员单个推送
                GtPush push = new GtPush();
                push.sendMessageToCidCustoms(jobj.toString(),memberBO.getGtCid(),info.getTitle(),userSrvConfig.getGtHost(),userSrvConfig.getGtMemberAppKey(),userSrvConfig.getGtMemberMasterSecret(),userSrvConfig.getGtMemberAppId());
            }else {
                //商家单个推送
                MerchantBO merchantBO = merchantService.findMemberByNum(info.getUserNum());
                GtPush push = new GtPush();
                push.sendMessageToCid(jobj.toString(),merchantBO.getGtCid(),info.getTitle(),userSrvConfig.getGtHost(),userSrvConfig.getGtMerchantAppKey(),userSrvConfig.getGtMerchantMasterSecret(),userSrvConfig.getGtMerchantAppId());
            }
        } else if (MqConstant.TOPIC_PROPERTY_SRV.equals(topic) && MqConstant.TAG_HANDLE_DEPOSIT.equals(tags)){
            //根据商家编号查询商家
            HandleDepostMessage info = (HandleDepostMessage) message;
            MerchantBO merchantBO = merchantService.findMemberByNum(info.getUserNum());
            //修改门店状态
            merchantStoreInfoService.updateMerchantStoreStatus(merchantBO.getId(), info.getStatusEnum().val);
            if(info.getShow()){
                merchantAuditService.setAuditInfoShow(merchantBO.getId());
            }
            if(info.getStatusEnum().val == MerchantStatusEnum.MERCHANT_STATUS_CANCEL.val.byteValue()){
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
            JSONObject contents = new JSONObject();
            contents.put("title",info.getTitle());
            contents.put("content",info.getContent());
            contents.put("type", MessageTypeEnum.getEnum(info.getMessageType()));
            GtPush push = new GtPush();
            logger.info("gtpush-all-type type:result({}) flag:flag({})", info.getUserType(),UserTypeEnum.MEMBER.val.byteValue()==info.getUserType());
           if(UserTypeEnum.MEMBER.val.byteValue()==info.getUserType()){
               //推送所有
               push.pushToAllUser(info.getTitle(),contents.toString(),userSrvConfig.getGtHost(),userSrvConfig.getGtMemberAppKey(),userSrvConfig.getGtMemberMasterSecret(),userSrvConfig.getGtMemberAppId());
           }else{
               push.pushToAllCompany(info.getTitle(),contents.toString(),userSrvConfig.getGtHost(),userSrvConfig.getGtMerchantAppKey(),userSrvConfig.getGtMerchantMasterSecret(),userSrvConfig.getGtMerchantAppId());
           }
        }else if (MqConstant.TOPIC_MALL_SRV.equals(topic) && MqConstant.TAG_GTPUSH_AREA.equals(tags)){
            //发送推送消息
            MessagePushInfo info = (MessagePushInfo) message;
            JSONObject contents = new JSONObject();
            contents.put("title",info.getTitle());
            contents.put("content",info.getContent());
            contents.put("type", MessageTypeEnum.getEnum(info.getMessageType()));
            GtPush push = new GtPush();
            List<MessagePushBO> messagePushBOS = null;
            logger.info("gtpush-area-type type:result({}) flag:flag({})", info.getUserType(),UserTypeEnum.MEMBER.val.byteValue()==info.getUserType());
            if(UserTypeEnum.MEMBER.val.byteValue()==info.getUserType()){
                //推送用户
                messagePushBOS = memberService.findMessagePushList(info.getArea());
                for(MessagePushBO messagePushBO : messagePushBOS){
                    push.sendMessageToCidCustoms(contents.toString(),messagePushBO.getGtCid(),info.getTitle(),userSrvConfig.getGtHost(),userSrvConfig.getGtMemberAppKey(),userSrvConfig.getGtMemberMasterSecret(),userSrvConfig.getGtMemberAppId());
                }
            }else{
                messagePushBOS = merchantService.findMessagePushList(info.getArea());
                for(MessagePushBO messagePushBO : messagePushBOS){
                    push.sendMessageToCid(contents.toString(),messagePushBO.getGtCid(),info.getTitle(),userSrvConfig.getGtHost(),userSrvConfig.getGtMerchantAppKey(),userSrvConfig.getGtMerchantMasterSecret(),userSrvConfig.getGtMerchantAppId());
                }
            }


        }
    }
}
