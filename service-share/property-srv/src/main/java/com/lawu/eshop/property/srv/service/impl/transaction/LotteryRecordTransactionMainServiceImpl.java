package com.lawu.eshop.property.srv.service.impl.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.Reply;
import com.lawu.eshop.compensating.transaction.annotation.CompensatingTransactionMain;
import com.lawu.eshop.compensating.transaction.impl.AbstractTransactionMainService;
import com.lawu.eshop.mq.constants.MqConstant;
import com.lawu.eshop.mq.dto.mall.LotteryRecordNotification;
import com.lawu.eshop.property.srv.bo.PointDetailBO;
import com.lawu.eshop.property.srv.constans.TransactionConstant;
import com.lawu.eshop.property.srv.service.PointDetailService;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
@Service("lotteryRecordTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.POINT_CONVERT_LOTTERY, topic = MqConstant.TOPIC_PROPERTY_SRV, tags = MqConstant.TAG_POINT_CONVERT_LOTTERY)
public class LotteryRecordTransactionMainServiceImpl extends AbstractTransactionMainService<LotteryRecordNotification, Reply> {

    @Autowired
    private PointDetailService pointDetailService;

    @Override
    public LotteryRecordNotification selectNotification(Long pointDetailId) {
        PointDetailBO pointDetailBO = pointDetailService.getPointDetailById(pointDetailId);
        LotteryRecordNotification notification = new LotteryRecordNotification();
        notification.setLotteryActivityId(Long.valueOf(pointDetailBO.getBizId()));
        notification.setUserNum(pointDetailBO.getUserNum());
        return notification;
    }

}
