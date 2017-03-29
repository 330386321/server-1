package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.core.page.PageParam;
import com.lawu.eshop.mall.constants.MessageStatusEnum;
import com.lawu.eshop.mall.dto.MessageDTO;
import com.lawu.eshop.mall.param.MessageParam;
import com.lawu.eshop.mall.srv.bo.MessageBO;
import com.lawu.eshop.mall.srv.bo.MessageStatisticsBO;
import com.lawu.eshop.mall.srv.converter.MessageConverter;
import com.lawu.eshop.mall.srv.domain.MessageDO;
import com.lawu.eshop.mall.srv.domain.MessageDOExample;
import com.lawu.eshop.mall.srv.mapper.MessageDOMapper;
import com.lawu.eshop.mall.srv.service.MessageService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * message service实现类
 * Created by zhangyong on 2017/3/29.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDOMapper messageDOMapper;

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
        Page<MessageBO> page = new Page<>();
        //设置记录总数
        page.setTotalCount(messageDOMapper.countByExample(example));
        page.setCurrentPage(pageParam.getCurrentPage());
        List<MessageDO> messageDOS = messageDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<MessageBO> messageBOS = new ArrayList<>();

        for (MessageDO messageDO : messageDOS) {
            MessageBO messageBO = MessageConverter.coverBO(messageDO);
            messageBOS.add(messageBO);
        }
        page.setRecords(messageBOS);

        return page;
    }
}
