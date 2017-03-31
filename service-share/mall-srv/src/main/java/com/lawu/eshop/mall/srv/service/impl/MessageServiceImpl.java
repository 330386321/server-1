package com.lawu.eshop.mall.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.MessageStatusEnum;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageParam;
import com.lawu.eshop.mall.param.MessageQueryParam;
import com.lawu.eshop.mall.srv.bo.MessageBO;
import com.lawu.eshop.mall.srv.bo.MessageStatisticsBO;
import com.lawu.eshop.mall.srv.converter.MessageConverter;
import com.lawu.eshop.mall.srv.domain.MessageDO;
import com.lawu.eshop.mall.srv.domain.MessageDOExample;
import com.lawu.eshop.mall.srv.domain.extend.MessageDOView;
import com.lawu.eshop.mall.srv.mapper.MessageDOMapper;
import com.lawu.eshop.mall.srv.mapper.extend.MessageDOMMapperExtend;
import com.lawu.eshop.mall.srv.service.MessageService;
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
    private MessageDOMMapperExtend messageDOMMapperExtend;

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

        MessageQueryParam messageQueryParam = new MessageQueryParam();
        messageQueryParam.setUserNum(userNum);
        messageQueryParam.setCurrentPage(pageParam.getCurrentPage());
        messageQueryParam.setPageSize(pageParam.getPageSize());
        //查询总数
        int totalCount = messageDOMMapperExtend.selectCountByUserNum(userNum);

        Page<MessageBO> page = new Page<>();
        //设置记录总数
        page.setTotalCount(totalCount);
        page.setCurrentPage(pageParam.getCurrentPage());
        List<MessageDOView> messageDOViews = messageDOMMapperExtend.selectByUserNum(messageQueryParam);
        List<MessageBO> messageBOS = new ArrayList<>();

        for (MessageDOView messageDO : messageDOViews) {
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
    public void saveMessage(String userNum, MessageInfoParam messageInfoParam) {
        MessageDO messageDO = new MessageDO();
        messageDO.setStatus(MessageStatusEnum.MESSAGE_STATUS_UNREAD.val);
        messageDO.setUserNum(userNum);
        messageDO.setType(messageInfoParam.getTypeEnum().val);
        messageDO.setContent(messageInfoParam.getContent());
        if (messageInfoParam.getRelateId() > 0) {
            messageDO.setRelateId(messageInfoParam.getRelateId());
        }
        messageDO.setGmtModified(new Date());
        messageDO.setGmtCreate(new Date());
        messageDOMapper.insert(messageDO);

    }
}
