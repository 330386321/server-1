package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@FeignClient(value = "mall-srv")
public interface TradeService {

    @RequestMapping(method = RequestMethod.GET, value = "trade/listTrade")
    Result listTrade();
}
