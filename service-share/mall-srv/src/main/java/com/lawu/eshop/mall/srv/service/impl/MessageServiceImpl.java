package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.MessageStatusEnum;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.param.*;
import com.lawu.eshop.mall.srv.bo.MessageBO;
import com.lawu.eshop.mall.srv.bo.MessageStatisticsBO;
import com.lawu.eshop.mall.srv.bo.MessageTemplateBO;
import com.lawu.eshop.mall.srv.converter.MessageConverter;
import com.lawu.eshop.mall.srv.domain.MessageDO;
import com.lawu.eshop.mall.srv.domain.MessageDOExample;
import com.lawu.eshop.mall.srv.domain.MessageTemplateDO;
import com.lawu.eshop.mall.srv.domain.MessageTemplateDOExample;
import com.lawu.eshop.mall.srv.mapper.MessageDOMapper;
import com.lawu.eshop.mall.srv.mapper.MessageTemplateDOMapper;
import com.lawu.eshop.mall.srv.service.MessageService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.user.MessagePushInfo;
import com.lawu.eshop.mq.message.MessageProducerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * message service实现类
 * Created by zhangyong on 2017/3/29.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDOMapper messageDOMapper;


    @Autowired
    private MessageProducerService messageProducerService;

    @Autowired
    private MessageTemplateDOMapper messageTemplateDOMapper;

    @Override
    public int selectNoReadCount(String userNum) {
        MessageDOExample example = new MessageDOExample();
        example.createCriteria().andUserNumEqualTo(userNum).andStatusEqualTo(MessageStatusEnum.MESSAGE_STATUS_UNREAD.val);
        int count = messageDOMapper.countByExample(example);
        return count;
    }

    @Override
    public MessageStatisticsBO selectLastMessage(String userNum) {

        MessageDOExample example = new MessageDOExample();
        example.createCriteria().andStatusEqualTo(MessageStatusEnum.MESSAGE_STATUS_UNREAD.val).andUserNumEqualTo(userNum);
        example.setOrderByClause("id desc");
        List<MessageDO> messageDOS = messageDOMapper.selectByExample(example);
        return messageDOS.isEmpty() ? null : MessageConverter.coverStatisticsBO(messageDOS.get(0));
    }

    @Override
    public Page<MessageBO> getMessageList(String userNum, MessageParam pageParam) {

       MessageDOExample example = new MessageDOExample();
       example.createCriteria().andUserNumEqualTo(userNum).andStatusNotEqualTo(MessageStatusEnum.MESSAGE_STATUS_DELETE.val);
       example.setOrderByClause("id desc");
        //查询总数
        RowBounds rowBounds = new RowBounds(pageParam.getOffset(), pageParam.getPageSize());

        int totalCount = messageDOMapper.countByExample(example);

        Page<MessageBO> page = new Page<>();
        //设置记录总数
        page.setTotalCount(totalCount);
        page.setCurrentPage(pageParam.getCurrentPage());
        List<MessageDO> messageDOS = messageDOMapper.selectByExampleWithRowbounds(example,rowBounds);
        List<MessageBO> messageBOS = new ArrayList<>();

        for (MessageDO messageDO : messageDOS) {
            MessageBO messageBO = MessageConverter.coverBO(messageDO);
            messageBOS.add(messageBO);
        }
        page.setRecords(messageBOS);
        return page;
    }

    @Override
    @Transactional
    public void updateMessageStatus(Long messageId, MessageStatusEnum statusEnum) {
        MessageDO messageDO = new MessageDO();
        messageDO.setId(messageId);
        messageDO.setStatus(statusEnum.val);
        messageDOMapper.updateByPrimaryKeySelective(messageDO);
    }

    @Override
    @Transactional
    public Integer saveMessage(String userNum, MessageInfoParam messageInfoParam) {
        MessageDO messageDO = new MessageDO();
        messageDO.setStatus(MessageStatusEnum.MESSAGE_STATUS_UNREAD.val);
        messageDO.setUserNum(userNum);
        messageDO.setType(messageInfoParam.getTypeEnum().val);
        //查询类型对应的消息模板
        MessageTemplateDOExample example = new MessageTemplateDOExample();
        example.createCriteria().andTypeEqualTo(messageInfoParam.getTypeEnum().val);
        List<MessageTemplateDO> dos = messageTemplateDOMapper.selectByExample(example);
        if(dos.isEmpty()){
            return  null;
        }
        //TODO 根据模板类型设置content
        /*if(MessageTypeEnum.MESSAGE_TYPE_INCOME.val == messageInfoParam.getTypeEnum().val){
        }*/
        messageDO.setContent(dos.get(0).getContent());
        messageDO.setTitle(dos.get(0).getTitle());
        if (messageInfoParam.getRelateId() != null && messageInfoParam.getRelateId() > 0) {
            messageDO.setRelateId(messageInfoParam.getRelateId());
        }
        messageDO.setGmtModified(new Date());
        messageDO.setGmtCreate(new Date());
       Integer id =  messageDOMapper.insert(messageDO);
       //发送推送
        MessagePushInfo pushInfo = new MessagePushInfo();
        pushInfo.setTitle(dos.get(0).getTitle());
        pushInfo.setContent(messageDO.getContent());
        pushInfo.setMessageId(messageDO.getId());
        pushInfo.setUserNum(messageDO.getUserNum());
        messageProducerService.sendMessage(MqConstant.TOPIC_MALL_SRV,MqConstant.TAG_GTPUSH,pushInfo);
       return  id;
    }

    @Override
    public MessageTemplateBO getTemplateByType(MessageTypeEnum typeEnum) {
        MessageTemplateDOExample example = new MessageTemplateDOExample();
        example.createCriteria().andTypeEqualTo(typeEnum.val);
        List<MessageTemplateDO> dos = messageTemplateDOMapper.selectByExample(example);
        if(dos.isEmpty()){
            return null;
        }
        return MessageConverter.coverTemplateBO(dos.get(0));
    }

    @Override
    @Transactional
    public Integer saveMessageOperator(String userNum,OperatorMessageInfoParam messageInfoParam) {
        MessageDO messageDO = new MessageDO();
        messageDO.setStatus(MessageStatusEnum.MESSAGE_STATUS_UNREAD.val);
        messageDO.setUserNum(userNum);
        messageDO.setType(MessageTypeEnum.MESSAGE_TYPE_PLATFORM_NOTICE.val);
        messageDO.setContent(messageInfoParam.getContent());
        messageDO.setGmtCreate(new Date());
        messageDO.setGmtModified(new Date());
       int row =  messageDOMapper.insert(messageDO);
        //发送推送
        MessagePushInfo pushInfo = new MessagePushInfo();
        pushInfo.setTitle(messageInfoParam.getTitle());
        pushInfo.setContent(messageInfoParam.getContent());
        pushInfo.setMessageId(messageDO.getId());
        pushInfo.setUserNum(userNum);
        messageProducerService.sendMessage(MqConstant.TOPIC_MALL_SRV,MqConstant.TAG_GTPUSH,pushInfo);
        return row;
    }

    @Override
    @Transactional
    public void saveMessageToAll(OperatorMessageParam param) {
        // 批量查询新增站内消息
        for(PushParam pushParam : param.getParams()){
            MessageDO messageDO = new MessageDO();
            messageDO.setStatus(MessageStatusEnum.MESSAGE_STATUS_UNREAD.val);
            messageDO.setUserNum(pushParam.getUserNum());
            messageDO.setType(MessageTypeEnum.MESSAGE_TYPE_PLATFORM_NOTICE.val);
            messageDO.setContent(param.getContent());
            messageDO.setGmtCreate(new Date());
            messageDO.setGmtModified(new Date());
            messageDOMapper.insert(messageDO);
        }
        MessagePushInfo pushInfo = new MessagePushInfo();
        pushInfo.setTitle(param.getTitle());
        pushInfo.setContent(param.getContent());
        pushInfo.setUserType(param.getUserTypeEnum().val);
        //推送全部
        if("all".equals(param.getArea())){
            messageProducerService.sendMessage(MqConstant.TOPIC_MALL_SRV,MqConstant.TAG_GTPUSHALL,pushInfo);
        }else{
            messageProducerService.sendMessage(MqConstant.TOPIC_MALL_SRV,MqConstant.TAG_GTPUSH_AREA,pushInfo);
        }
    }
}
