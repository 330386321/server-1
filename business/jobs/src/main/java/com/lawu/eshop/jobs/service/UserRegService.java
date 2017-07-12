package com.lawu.eshop.jobs.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.param.UserRegAreaParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/6/29.
 */
@FeignClient(value = "statistics-srv")
public interface UserRegService {

    /**
     * 按日统计用户注册量
     *
     * @param memberCount
     * @param merchantCount
     */
    @RequestMapping(value = "userReg/saveUserRegDaily", method = RequestMethod.POST)
    Result saveUserRegDaily(@RequestParam("memberCount") Integer memberCount, @RequestParam("merchantCount") Integer merchantCount);

    /**
     * 按月统计用户注册量
     *
     * @param memberCount
     * @param merchantCount
     */
    @RequestMapping(value = "userReg/saveUserRegMonth", method = RequestMethod.POST)
    Result saveUserRegMonth(@RequestParam("memberCount") Integer memberCount, @RequestParam("merchantCount") Integer merchantCount);


    /**
     * 按市级更新用户注册量
     *
     * @param param
     */
    @RequestMapping(value = "userReg/updateUserRegArea", method = RequestMethod.POST)
    Result updateUserRegArea(@RequestBody UserRegAreaParam param);

}