package com.lawu.eshop.jobs.service;

import com.lawu.eshop.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/7/27.
 */
@FeignClient(value = "cache-srv")
public interface RecommendStoreCacheService {

    /**
     * 新店推荐
     *
     * @param regionPath
     * @param storeInfo
     * @return
     */
    @RequestMapping(value = "recommendStore/saveNewMerchant", method = RequestMethod.POST)
    Result saveNewMerchant(@RequestParam("regionPath") String regionPath, @RequestBody String storeInfo);

    /**
     * 优选美食-人气最高
     *
     * @param regionPath
     * @param storeInfo
     * @return
     */
    @RequestMapping(value = "recommendStore/saveRecommendFoodConsume", method = RequestMethod.POST)
    Result saveRecommendFoodConsume(@RequestParam("regionPath") String regionPath, @RequestBody String storeInfo);

    /**
     * 优选美食-评价最高
     *
     * @param regionPath
     * @param storeInfo
     * @return
     */
    @RequestMapping(value = "recommendStore/saveRecommendFoodComment", method = RequestMethod.POST)
    Result saveRecommendFoodComment(@RequestParam("regionPath") String regionPath, @RequestBody String storeInfo);

}
