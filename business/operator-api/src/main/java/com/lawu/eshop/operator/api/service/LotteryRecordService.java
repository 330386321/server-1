package com.lawu.eshop.operator.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.LotteryRecordOperatorDTO;
import com.lawu.eshop.mall.query.OperatorLotteryRecordQuery;

/**
 * @author meishuquan
 * @date 2017/11/24.
 */
@FeignClient(value = "mall-srv")
public interface LotteryRecordService {

    /**
     * 运营平台查询抽奖列表
     *
     * @param query
     * @return
     * @author meishuquan
     */
    @RequestMapping(method = RequestMethod.POST, value = "lotteryRecord/listOperatorLotteryRecord")
    Result<Page<LotteryRecordOperatorDTO>> listOperatorLotteryRecord(@ModelAttribute OperatorLotteryRecordQuery query);

}
