package com.lawu.eshop.merchant.api.service;


import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.hystrix.MerchantServiceHystrix;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MerchantStoreDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
@FeignClient(value = "user-srv")
public interface MerchantStoreService {


    /**
     * 根据商家id查询门店信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "merchantStore/findMerchantStoreInfo/{id}")
    Result<MerchantStoreDTO> selectMerchantStore(@PathVariable("id") Long id);
}
