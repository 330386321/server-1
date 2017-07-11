package com.lawu.eshop.member.api.service;

import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangyong
 * @date 2017/7/3.
 */
@FeignClient(value = "cache-srv")
public interface UserVisitService {

    @RequestMapping(value = "userVisit/addUserVisitCount",method = RequestMethod.POST)
    Result addUserVisitCount(@RequestParam("userNum") String userNum,
                             @RequestParam("nowTimeStr") String nowTimeStr,
                             @RequestParam("userId") Long userId, @RequestParam("type") UserType type);
}
