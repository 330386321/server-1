package com.lawu.eshop.mall.srv.converter;

import com.lawu.eshop.mall.srv.bo.MessageStatisticsBO;
import com.lawu.eshop.mall.srv.domain.MessageDO;

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
}
