package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.constants.MessageStatusEnum;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.dto.MessageDTO;
import com.lawu.eshop.mall.srv.bo.MessageBO;
import com.lawu.eshop.mall.srv.bo.MessageStatisticsBO;
import com.lawu.eshop.mall.srv.domain.MessageDO;

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
        return messageBO;
    }

    public static List<MessageDTO> coverDTOS(List<MessageBO> messageBOS) {
        if (messageBOS.isEmpty()) {
            return null;
        }
        List<MessageDTO> messageDTOS = new ArrayList<>();
        for (MessageBO messageBO : messageBOS) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setStatusEnum(MessageStatusEnum.getEnum(messageBO.getStatus()));
            messageDTO.setId(messageBO.getId());
            messageDTO.setMessageTypeEnum(MessageTypeEnum.getEnum(messageBO.getType()));
            messageDTO.setContent(messageBO.getContent());
            messageDTOS.add(messageDTO);
        }
        return messageDTOS;
    }
}
