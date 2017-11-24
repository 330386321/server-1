package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.mall.param.LotteryRecordParam;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
public interface LotteryRecordService {

    /**
     * 保存抽奖记录
     *
     * @param param
     * @author meishuquan
     */
    void saveLotteryRecord(LotteryRecordParam param);

    /**
     * 根据用户编号和抽奖活动id更新抽奖次数
     *
     * @param userNum
     * @param lotteryActivityId
     */
    void updateLotteryCountByUserNumAndLotteryActivityId(String userNum, Long lotteryActivityId);

}
