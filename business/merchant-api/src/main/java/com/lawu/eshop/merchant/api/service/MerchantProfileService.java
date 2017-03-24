package com.lawu.eshop.merchant.api.service;


import com.lawu.eshop.merchant.api.service.hystrix.MerchantProfileServiceHystrix;
import com.lawu.eshop.user.dto.MerchantProfileDTO;
import com.lawu.eshop.user.dto.param.MerchantProfileParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-srv")
public interface MerchantProfileService {

    /**
     * 设置网站链接
     * @param merchantProfileParam
     */
    @RequestMapping(method = RequestMethod.POST, value = "merchantprofile/updateMerchantSizeLink")
    void updateMerchantSizeLink(@RequestBody MerchantProfileParam merchantProfileParam);

    /**
     * 查询商家主页基本信息
     * @param merchantProfileId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value ="merchantprofile/findMerchantProfileInfo")
    MerchantProfileDTO findMerchantProfileInfo(@RequestParam("merchantProfileId") Long merchantProfileId);
}
