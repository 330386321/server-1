package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.LotteryActivityDTO;
import com.lawu.eshop.mall.param.LotteryActivityRealParam;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
@FeignClient(value = "mall-srv")
public interface LotteryActivityService {

    /**
     * 抽奖活动列表
     *
     * @param param
     * @return
     * @author meishuquan
     */
    @RequestMapping(method = RequestMethod.POST, value = "lotteryActivity/listLotteryActivity")
    Result<Page<LotteryActivityDTO>> listLotteryActivity(LotteryActivityRealParam param);
}
