package com.lawu.eshop.jobs.service;

import com.lawu.eshop.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
@FeignClient(value = "statistics-srv")
public interface CollectionUserActiveService {

    @RequestMapping(value = "userActive/collectionMemberActiveDaily", method = RequestMethod.GET)
    Result<Integer> collectionMemberActiveDaily();

    @RequestMapping(value = "userActive/collectionMerchantActiveDaily", method = RequestMethod.GET)
    Result<Integer> collectionMerchantActiveDaily();
}
