package com.lawu.eshop.mall.srv.service;

import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.LotteryRecordParam;
import com.lawu.eshop.mall.query.LotteryRecordQuery;
import com.lawu.eshop.mall.query.OperatorLotteryRecordQuery;
import com.lawu.eshop.mall.srv.bo.LotteryInfoBO;
import com.lawu.eshop.mall.srv.bo.LotteryRecordBO;
import com.lawu.eshop.mall.srv.bo.LotteryRecordOperatorBO;

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

    /**
     * 查询中奖滚动列表
     *
     * @return
     * @author meishuquan
     */
    List<LotteryInfoBO> listLotteryInfo();

    /**
     * 查询中奖公告列表
     *
     * @param query
     * @return
     * @author meishuquan
     */
    Page<LotteryRecordBO> listLotteryRecord(LotteryRecordQuery query);

    /**
     * 运营平台查询参与抽奖列表
     *
     * @param query
     * @return
     * @author meishuquan
     */
    Page<LotteryRecordOperatorBO> listOperatorLotteryRecord(OperatorLotteryRecordQuery query);

}
