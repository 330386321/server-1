package com.lawu.eshop.mall.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.constants.MessageStatusEnum;
import com.lawu.eshop.mall.constants.MessageTypeEnum;
import com.lawu.eshop.mall.param.MessageInfoParam;
import com.lawu.eshop.mall.param.MessageParam;
import com.lawu.eshop.mall.param.MessageQueryParam;
import com.lawu.eshop.mall.param.OperatorMessageInfoParam;
import com.lawu.eshop.mall.param.OperatorMessageParam;
import com.lawu.eshop.mall.param.PushParam;
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
        List<MessageDO> messageDOS = messageDOMapper.selectByExampleWithRowbounds(example, rowBounds);
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
    public void updateMessageStatus(Long messageId, MessageStatusEnum statusEnum, String userNum) {
        MessageDO messageDO = new MessageDO();
        messageDO.setStatus(statusEnum.val);
        MessageDOExample example = new MessageDOExample();
        example.createCriteria().andIdEqualTo(messageId).andUserNumEqualTo(userNum);
        messageDOMapper.updateByExampleSelective(messageDO,example);
    }

    @Override
    @Transactional
    public Integer saveMessage(String userNum, MessageInfoParam messageInfoParam) {
        MessageDO messageDO = new MessageDO();
        messageDO.setStatus(MessageStatusEnum.MESSAGE_STATUS_UNREAD.val);
        messageDO.setUserNum(userNum);
        messageDO.setType(messageInfoParam.getTypeEnum().getVal());
        if(messageInfoParam.getIsPush()!=null){
        	messageDO.setIsPush(messageInfoParam.getIsPush());
        }
        //查询类型对应的消息模板
        MessageTemplateDOExample example = new MessageTemplateDOExample();
        example.createCriteria().andTypeEqualTo(messageInfoParam.getTypeEnum().getVal());
        example.setOrderByClause("id desc");
        List<MessageTemplateDO> dos = messageTemplateDOMapper.selectByExample(example);
        if (dos.isEmpty()) {
            return null;
        }

        String content = dos.get(0).getContent();
        /**
         * {0}用户昵称、{1}订单编号、{2}运单编号、{3}余额、{4}充值金额、{5}当前积分、{6}消费金额
         * {7}优惠金额、{8}退款编号、{9}商品名称、{10}收益金额、{11}收益积分、{12}商家名称
         * {13}广告名称、{14}门店名称、{15}消费积分、{16}充值编号、{17}广告类型名称、{18}失败原因
         * {19}代发货商品数量、{20}快递名称、{21}退款金额、{22}订单金额、{23}工单的问题、{24}工单回复的内容
         * {25}充值积分
         */

        //消息替换占位符
        if (messageInfoParam.getMessageParam() != null) {

            content = content.replace("{0}", messageInfoParam.getMessageParam().getUserName() == null ? "" : messageInfoParam.getMessageParam().getUserName());
            content = content.replace("{1}", messageInfoParam.getMessageParam().getOrderNum() == null ? "" : messageInfoParam.getMessageParam().getOrderNum());
            content = content.replace("{2}", messageInfoParam.getMessageParam().getWaybillNum() == null ? "" : messageInfoParam.getMessageParam().getWaybillNum());
            content = content.replace("{3}", messageInfoParam.getMessageParam().getBalance() == null ? "" : messageInfoParam.getMessageParam().getBalance().toString());
            content = content.replace("{4}", messageInfoParam.getMessageParam().getRechargeBalance() == null ? "" : messageInfoParam.getMessageParam().getRechargeBalance().toString());
            content = content.replace("{5}", messageInfoParam.getMessageParam().getPoint() == null ? "" : messageInfoParam.getMessageParam().getPoint().toString());
            content = content.replace("{6}", messageInfoParam.getMessageParam().getExpendAmount() == null ? "" : messageInfoParam.getMessageParam().getExpendAmount().toString());
            content = content.replace("{7}", messageInfoParam.getMessageParam().getFavoredAmount() == null ? "" : messageInfoParam.getMessageParam().getFavoredAmount().toString());
            content = content.replace("{8}", messageInfoParam.getMessageParam().getRefundNum() == null ? "" : messageInfoParam.getMessageParam().getRefundNum());
            content = content.replace("{9}", messageInfoParam.getMessageParam().getProductName() == null ? "" : messageInfoParam.getMessageParam().getProductName());
            content = content.replace("{10}", messageInfoParam.getMessageParam().getEarningAmount() == null ? "" : messageInfoParam.getMessageParam().getEarningAmount().toString());
            content = content.replace("{11}", messageInfoParam.getMessageParam().getEarningPoint() == null ? "" : messageInfoParam.getMessageParam().getEarningPoint().toString());
            content = content.replace("{12}", messageInfoParam.getMessageParam().getMerchantName() == null ? "" : messageInfoParam.getMessageParam().getMerchantName());
            content = content.replace("{13}", messageInfoParam.getMessageParam().getAdName() == null ? "" : messageInfoParam.getMessageParam().getAdName());
            content = content.replace("{14}", messageInfoParam.getMessageParam().getStoreName() == null ? "" : messageInfoParam.getMessageParam().getStoreName());
            content = content.replace("{15}", messageInfoParam.getMessageParam().getExpendPoint() == null ? "" : messageInfoParam.getMessageParam().getExpendPoint().toString());
            content = content.replace("{16}", messageInfoParam.getMessageParam().getRechargeNum() == null ? "" : messageInfoParam.getMessageParam().getRechargeNum());
            content = content.replace("{17}", messageInfoParam.getMessageParam().getAdTypeName() == null ? "" : messageInfoParam.getMessageParam().getAdTypeName());
            content = content.replace("{18}", messageInfoParam.getMessageParam().getFailReason() == null ? "" : messageInfoParam.getMessageParam().getFailReason());
            content = content.replace("{19}", messageInfoParam.getMessageParam().getProductCount() == null ? "" : messageInfoParam.getMessageParam().getProductCount().toString());
            content = content.replace("{20}", messageInfoParam.getMessageParam().getExpressCompanyName() == null ? "" : messageInfoParam.getMessageParam().getExpressCompanyName());
            content = content.replace("{21}", messageInfoParam.getMessageParam().getRefundAmount() == null ? "" : messageInfoParam.getMessageParam().getRefundAmount().toString());
            content = content.replace("{22}", messageInfoParam.getMessageParam().getOrderAmount() == null ? "" : messageInfoParam.getMessageParam().getOrderAmount().toString());
            content = content.replace("{23}", messageInfoParam.getMessageParam().getWorkOrderContent() == null ? "" : messageInfoParam.getMessageParam().getWorkOrderContent().toString());
            content = content.replace("{24}", messageInfoParam.getMessageParam().getReplyWorkOrderContent() == null ? "" : messageInfoParam.getMessageParam().getReplyWorkOrderContent().toString());
            content = content.replace("{25}", messageInfoParam.getMessageParam().getRechargePoint() == null ? "" : messageInfoParam.getMessageParam().getRechargePoint().toString());
        }
        messageDO.setContent(content);
        messageDO.setTitle(dos.get(0).getTitle());
        if (messageInfoParam.getRelateId() != null && messageInfoParam.getRelateId() > 0) {
            messageDO.setRelateId(messageInfoParam.getRelateId());
        }else{
            messageDO.setRelateId(0L);
        }
        messageDO.setGmtModified(new Date());
        messageDO.setGmtCreate(new Date());
        Integer id = messageDOMapper.insert(messageDO);
        //发送推送
        MessagePushInfo pushInfo = new MessagePushInfo();
        pushInfo.setTitle(dos.get(0).getTitle());
        pushInfo.setContent(content);
        pushInfo.setMessageId(messageDO.getId());
        pushInfo.setUserNum(userNum);
        pushInfo.setMessageType(messageInfoParam.getTypeEnum().getVal());
        if(messageInfoParam.getRelateId() == null){
            pushInfo.setRelateId(0L);
        }else{
            pushInfo.setRelateId(messageInfoParam.getRelateId());
        }
        messageProducerService.sendMessage(MqConstant.TOPIC_MALL_SRV, MqConstant.TAG_GTPUSH, pushInfo);
        return id;
    }

    @Override
    public MessageTemplateBO getTemplateByType(MessageTypeEnum typeEnum) {
        MessageTemplateDOExample example = new MessageTemplateDOExample();
        example.createCriteria().andTypeEqualTo(typeEnum.getVal());
        List<MessageTemplateDO> dos = messageTemplateDOMapper.selectByExample(example);
        if (dos.isEmpty()) {
            return null;
        }
        return MessageConverter.coverTemplateBO(dos.get(0));
    }

    @Override
    @Transactional
    public Integer saveMessageOperator(String userNum, OperatorMessageInfoParam messageInfoParam) {
        MessageDO messageDO = new MessageDO();
        messageDO.setStatus(MessageStatusEnum.MESSAGE_STATUS_UNREAD.val);
        messageDO.setUserNum(userNum);
        messageDO.setType(MessageTypeEnum.MESSAGE_TYPE_PLATFORM_NOTICE.getVal());
        messageDO.setContent(messageInfoParam.getContent());
        messageDO.setGmtCreate(new Date());
        messageDO.setGmtModified(new Date());
        messageDO.setTitle(messageInfoParam.getTitle());
        int row = messageDOMapper.insert(messageDO);
        //发送推送
        MessagePushInfo pushInfo = new MessagePushInfo();
        pushInfo.setTitle(messageInfoParam.getTitle());
        pushInfo.setContent(messageInfoParam.getContent());
        pushInfo.setMessageId(messageDO.getId());
        pushInfo.setUserNum(userNum);
        pushInfo.setMessageType(MessageTypeEnum.MESSAGE_TYPE_PLATFORM_NOTICE.getVal());
        messageProducerService.sendMessage(MqConstant.TOPIC_MALL_SRV, MqConstant.TAG_GTPUSH, pushInfo);
        return row;
    }

    @Override
    @Transactional
    public void saveMessageToAll(OperatorMessageParam param) {
        // 批量查询新增站内消息
        for (PushParam pushParam : param.getParams()) {
            MessageDO messageDO = new MessageDO();
            messageDO.setStatus(MessageStatusEnum.MESSAGE_STATUS_UNREAD.val);
            messageDO.setUserNum(pushParam.getUserNum());
            messageDO.setType(MessageTypeEnum.MESSAGE_TYPE_PLATFORM_NOTICE.getVal());
            messageDO.setTitle(param.getTitle());
            messageDO.setContent(param.getContent());
            messageDO.setGmtCreate(new Date());
            messageDO.setGmtModified(new Date());
            messageDOMapper.insert(messageDO);
        }
        MessagePushInfo pushInfo = new MessagePushInfo();
        pushInfo.setTitle(param.getTitle());
        pushInfo.setContent(param.getContent());
        pushInfo.setUserType(param.getUserTypeEnum().getVal());
        pushInfo.setMessageType(MessageTypeEnum.MESSAGE_TYPE_PLATFORM_NOTICE.getVal());
        //推送全部
        if ("all".equals(param.getArea())) {
            messageProducerService.sendMessage(MqConstant.TOPIC_MALL_SRV, MqConstant.TAG_GTPUSHALL, pushInfo);
        } else {
            messageProducerService.sendMessage(MqConstant.TOPIC_MALL_SRV, MqConstant.TAG_GTPUSH_AREA, pushInfo);
        }
    }

    @Override
    public Page<MessageBO> getOperatorMessageList(MessageQueryParam param) {
        MessageDOExample example = new MessageDOExample();
        if (StringUtils.isNotEmpty(param.getUserNum())) {
            example.createCriteria().andUserNumEqualTo(param.getUserNum()).andStatusNotEqualTo(MessageStatusEnum.MESSAGE_STATUS_DELETE.val);
        } else {
            example.createCriteria().andStatusNotEqualTo(MessageStatusEnum.MESSAGE_STATUS_DELETE.val);
        }
        example.setOrderByClause("id desc");

        RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
        int total = messageDOMapper.countByExample(example);

        List<MessageDO> messageDOS = messageDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        if (messageDOS.isEmpty()) {
            return null;
        }
        Page<MessageBO> page = new Page<>();
        List<MessageBO> messageBOS = new ArrayList<>();
        for (MessageDO messageDO : messageDOS) {
            MessageBO messageBO = MessageConverter.coverBO(messageDO);
            messageBOS.add(messageBO);
        }
        page.setCurrentPage(param.getCurrentPage());
        page.setTotalCount(total);
        page.setRecords(messageBOS);
        return page;
    }

    @Override
    public MessageBO selectMessageId(Long id) {
        MessageDO messageDO = messageDOMapper.selectByPrimaryKey(id);
        return MessageConverter.coverBO(messageDO);
    }

	@Override
	public void pushMessageBySetCid(String userNum,MessageTypeEnum typeEnum) {
		MessageDOExample example = new MessageDOExample();
		example.createCriteria().andIsPushEqualTo(false).andTypeEqualTo(typeEnum.getVal()).andUserNumEqualTo(userNum);
		List<MessageDO> list = messageDOMapper.selectByExample(example);
		for (MessageDO messageDO : list) {
			//发送推送
	        MessagePushInfo pushInfo = new MessagePushInfo();
	        pushInfo.setTitle(messageDO.getTitle());
	        pushInfo.setContent(messageDO.getContent());
	        pushInfo.setMessageId(messageDO.getId());
	        pushInfo.setUserNum(userNum);
	        pushInfo.setMessageType(messageDO.getType());
	        if(messageDO.getRelateId() == null){
	            pushInfo.setRelateId(0L);
	        }else{
	            pushInfo.setRelateId(messageDO.getRelateId());
	        }
	        messageProducerService.sendMessage(MqConstant.TOPIC_MALL_SRV, MqConstant.TAG_GTPUSH, pushInfo);
	        
	        MessageDO record = new MessageDO();
	        record.setIsPush(true);
	        record.setGmtModified(new Date());
	        record.setId(messageDO.getId());
	        
	        messageDOMapper.updateByPrimaryKeySelective(record);
	        
		}
	}
}
