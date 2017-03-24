package com.lawu.eshop.merchant.api.service;


import com.lawu.eshop.user.dto.MerchantInfoDTO;
import com.lawu.eshop.user.dto.param.MerchantProfileParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-srv")
public interface MerchantInfoService {

    /**
     * 设置网站链接
     * @param merchantProfileParam
     */
    @RequestMapping(method = RequestMethod.PUT, value = "merchantInfo/updateMerchantSizeLink")
    int updateMerchantSizeLink(@RequestBody MerchantProfileParam merchantProfileParam, @RequestParam("id") Long id);

    /**
     * 查询商家主页基本信息
     * @param merchantProfileId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value ="merchantInfo/findMerchantProfileInfo/{merchantProfileId}")
    MerchantInfoDTO findMerchantProfileInfo(@PathVariable("merchantProfileId") Long merchantProfileId);
}
