package com.lawu.eshop.jobs.service;

import com.lawu.eshop.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
@FeignClient(value = "statistics-srv")
public interface UserActiveService {

    @RequestMapping(value = "userActive/saveUserActiveDaily", method = RequestMethod.GET)
    Result saveUserActiveDaily(@RequestParam(value = "memberCount") Integer memberCount,
                               @RequestParam(value = "merchantCount") Integer merchantCount);

    @RequestMapping(value = "userActive/saveUserActiveMonth", method = RequestMethod.GET)
    Result saveUserActiveMonth(@RequestParam(value = "memberCount") Integer memberCount,
                               @RequestParam(value = "merchantCount") Integer merchantCount);
}
