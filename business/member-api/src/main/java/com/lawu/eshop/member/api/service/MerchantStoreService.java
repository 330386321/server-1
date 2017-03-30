package com.lawu.eshop.member.api.service;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;

/**
 * 
 * @author Sunny
 * @date 2017/3/30
 */
@FeignClient(value = "user-srv")
public interface MerchantStoreService {

    /**
     * 根据商家ID获取商家门店的名称
     * 
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "merchantStore/getNameBymerchantId/{merchantId}", method = RequestMethod.GET)
    public Result<String> getNameByMerchantId(@PathVariable("merchantId") Long merchantId);

}
