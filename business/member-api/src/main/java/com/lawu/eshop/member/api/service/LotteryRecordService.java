package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.LotteryRecordParam;

/**
 * @author meishuquan
 * @date 2017/11/23.
 */
@FeignClient(value = "mall-srv")
public interface LotteryRecordService {

    @RequestMapping(method = RequestMethod.POST, value = "lotteryRecord/saveLotteryRecord")
    Result saveLotteryRecord(@ModelAttribute LotteryRecordParam param);

}
