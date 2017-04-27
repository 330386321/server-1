package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.constants.MessageStatusReturnEnum;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.dto.MessageDTO;
import com.lawu.eshop.mall.dto.OperatorMessageDTO;
import com.lawu.eshop.mall.srv.bo.MessageBO;
import com.lawu.eshop.mall.srv.bo.MessageStatisticsBO;
import com.lawu.eshop.mall.srv.bo.MessageTemplateBO;
import com.lawu.eshop.mall.srv.domain.MessageDO;
import com.lawu.eshop.mall.srv.domain.MessageTemplateDO;

import java.util.ArrayList;
import java.util.List;

/**
 * 站内信息转换类
 * Created by Administrator on 2017/3/29.
 */
public class MessageConverter {

    public static MessageStatisticsBO coverStatisticsBO(MessageDO messageDO) {
        if (messageDO == null) {
            return null;
        }
        MessageStatisticsBO messageStatisticsBO = new MessageStatisticsBO();
        messageStatisticsBO.setContent(messageDO.getContent());
        messageStatisticsBO.setType(messageDO.getType());
        return messageStatisticsBO;
    }

    public static MessageBO coverBO(MessageDO messageDO) {
        if (messageDO == null) {
            return null;
        }
        MessageBO messageBO = new MessageBO();
        messageBO.setContent(messageDO.getContent());
        messageBO.setType(messageDO.getType());
        messageBO.setId(messageDO.getId());
        messageBO.setStatus(messageDO.getStatus());
        messageBO.setTitle(messageDO.getTitle());
        messageBO.setUserNum(messageDO.getUserNum());
        messageBO.setGmtCreate(messageDO.getGmtCreate());
        return messageBO;
    }

    public static List<MessageDTO> coverDTOS(List<MessageBO> messageBOS) {
        if (messageBOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<MessageDTO> messageDTOS = new ArrayList<>();
        for (MessageBO messageBO : messageBOS) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setStatusEnum(MessageStatusReturnEnum.getEnum(messageBO.getStatus()));
            messageDTO.setId(messageBO.getId());
            messageDTO.setMessageTypeEnum(MessageTypeEnum.getEnum(messageBO.getType()));
            messageDTO.setContent(messageBO.getContent());
            messageDTO.setTitle(messageBO.getTitle());
            messageDTO.setGmtCreate(messageBO.getGmtCreate());
            messageDTOS.add(messageDTO);
        }
        return messageDTOS;
    }

    public static MessageTemplateBO coverTemplateBO(MessageTemplateDO messageTemplateDO) {
        if(messageTemplateDO == null){
            return null;
        }
        MessageTemplateBO messageTemplateBO = new MessageTemplateBO();
        messageTemplateBO.setId(messageTemplateDO.getId());
        messageTemplateBO.setContent(messageTemplateDO.getContent());
        messageTemplateBO.setTitle(messageTemplateDO.getTitle());
        messageTemplateBO.setType(messageTemplateDO.getType());
        return messageTemplateBO;
    }

    public static List<OperatorMessageDTO> coverOperatorDTOS(List<MessageBO> records) {
        if(records == null ){
            return new ArrayList<>();
        }
        List<OperatorMessageDTO> list = new ArrayList<>();
        for(MessageBO messageBO : records){
            OperatorMessageDTO messageDTO = new OperatorMessageDTO();
            messageDTO.setUserNum(messageBO.getUserNum());
            messageDTO.setId(messageBO.getId());
            messageDTO.setTitle(messageBO.getTitle());
            messageDTO.setContent(messageBO.getContent());
            messageDTO.setGmtCreate(messageBO.getGmtCreate());
            messageDTO.setMessageType(MessageTypeEnum.getEnum(messageBO.getType()));
            list.add(messageDTO);
        }
        return list;
    }
}
