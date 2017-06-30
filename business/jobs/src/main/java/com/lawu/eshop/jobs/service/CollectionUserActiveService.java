package com.lawu.eshop.jobs.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.UserActiveDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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

    @RequestMapping(value = "userActive/collectionMemberActiveMonth", method = RequestMethod.GET)
    Result<Integer> collectionMemberActiveMonth();

    @RequestMapping(value = "userActive/collectionMerchantActiveMonth", method = RequestMethod.GET)
    Result<Integer> collectionMerchantActiveMonth();

    @RequestMapping(value = "userActive/collectionMemberActiveAreaDaily", method = RequestMethod.GET)
    Result<List<UserActiveDTO>> collectionMemberActiveAreaDaily();

    @RequestMapping(value = "userActive/collectionMerchantActiveAreaDaily", method = RequestMethod.GET)
    Result<List<UserActiveDTO>> collectionMerchantActiveAreaDaily();
}
