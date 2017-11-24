package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.query.LotteryActivityRealQuery;
import com.lawu.eshop.mall.srv.bo.LotteryActivityBO;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
public interface LotteryActivityService {

    /**
     * 抽奖活动列表
     *
     * @param query
     * @return
     * @author meishuquan
     */
    Page<LotteryActivityBO> listLotteryActivity(LotteryActivityRealQuery query);

    /**
     * 根据id查询抽奖活动
     *
     * @param id
     * @return
     * @author meishuquan
     */
    LotteryActivityBO getLotteryActivityById(Long id);

}
