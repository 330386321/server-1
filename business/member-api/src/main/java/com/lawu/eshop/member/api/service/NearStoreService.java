package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;

/**
 * @author meishuquan
 * @date 2017/4/5.
 */
@FeignClient(value = "user-srv")
public interface NearStoreService {

    /**
     * 查询附近门店
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "nearStore/listNearStore")
    Result listNearStore(@RequestParam("longitude") Double longitude, @RequestParam("latitude") Double latitude,@RequestParam("industryPath")String industryPath);
}
