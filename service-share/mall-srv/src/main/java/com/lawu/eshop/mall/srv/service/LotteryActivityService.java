package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.LotteryActivityRealParam;
import com.lawu.eshop.mall.srv.bo.LotteryActivityBO;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
public interface LotteryActivityService {

    /**
     * 抽奖活动列表
     *
     * @param param
     * @return
     * @author meishuquan
     */
    Page<LotteryActivityBO> listLotteryActivity(LotteryActivityRealParam param);
}
