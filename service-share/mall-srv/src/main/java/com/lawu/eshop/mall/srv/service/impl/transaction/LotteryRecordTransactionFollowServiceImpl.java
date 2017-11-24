package com.lawu.eshop.mall.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionFollow;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionFollowService;
import com.lawu.eshop.mall.srv.service.LotteryRecordService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.mall.LotteryRecordNotification;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
@Service("lotteryRecordTransactionFollowServiceImpl")
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_PROPERTY_SRV, tags = MqConstant.TAG_POINT_CONVERT_LOTTERY)
public class LotteryRecordTransactionFollowServiceImpl extends AbstractTransactionFollowService<LotteryRecordNotification, Reply> {

    @Autowired
    private LotteryRecordService lotteryRecordService;

    @Override
    public void execute(LotteryRecordNotification notification) {
        lotteryRecordService.updateLotteryCountByUserNumAndLotteryActivityId(notification.getUserNum(), notification.getLotteryActivityId());
    }

}
