package com.lawu.eshop.order.srv.service.impl.transaction;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.order.PayOrderNotification;
import com.lawu.eshop.mq.dto.user.FansInfo;
import com.lawu.eshop.mq.message.MessageProducerService;
import com.lawu.eshop.order.constants.PayOrderStatusEnum;
import com.lawu.eshop.order.srv.domain.PayOrderDO;
import com.lawu.eshop.order.srv.mapper.PayOrderDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangyong
 * @date 2017/4/11.
 */
@Service("payOrderTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_PROPERTY_SRV, tags = MqConstant.TAG_PAYORDER)
public class PayOrderTransactionFollowServiceImpl extends AbstractTransactionFollowService<PayOrderNotification, Reply> {

    @Autowired
    private PayOrderDOMapper payOrderDOMapper;

    @Autowired
    private MessageProducerService messageProducerService;

    @Override
    @Transactional
    public Reply execute(PayOrderNotification notification) {
        Long payOrderId = notification.getPayOrderId();
        //更改订单状态
        PayOrderDO payOrderDO = new PayOrderDO();
        payOrderDO.setId(payOrderId);
        payOrderDO.setStatus(PayOrderStatusEnum.STATUS_PAY_SUCCESS.getVal());
        payOrderDOMapper.updateByPrimaryKeySelective(payOrderDO);

        PayOrderDO oldOrder = payOrderDOMapper.selectByPrimaryKey(payOrderId);
        //发送消息增加买单笔数并成为粉丝
        FansInfo fansInfo = new FansInfo();
        fansInfo.setMemberId(oldOrder.getMemberId());
        fansInfo.setMerchantId(oldOrder.getMerchantId());
        messageProducerService.sendMessage(MqConstant.TOPIC_ORDER_SRV,MqConstant.TAG_BUY_NUMBERS,fansInfo);

        return new Reply();
    }
}